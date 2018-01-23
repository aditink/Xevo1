const functions = require('firebase-functions');

// The Firebase Admin SDK to access the Firebase Realtime Database. 
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

/**
 * Extract answer from dictionary returned from Firebase.
 * answer_dict : Dictionary is of the form:
 * V{
 *   A: Ib {
 *       B: *the thing we want*
 *       other stuff
 *    }
 *   Other stuff
 * }
 */
function extract(answer_dict) {
    return answer_dict['A']['B'];
}


/**
 * Extract string from dictionary returned from Firebase.
 * answer_dict : Dictionary is of the form:
 * V{
 *   A: Ib {
 *       B: "the string we want"
 *       other stuff
 *    }
 *   Other stuff
 * }
 */
function extractString(answer_dict) {
    return extract(answer_dict).toString();
}

/**
 * Triggers when a user gets a new answer and sends a notification.
 *
 * Consultant changes status in `/Cases/{caseid}/{status}` to ANSWERED.
 * Client id is available at `/Cases/{caseid}/client`.
 * Users save their device notification tokens to `/Users/client/device/{notificationToken}`.
 */
exports.sendAnswerNotification = functions.database.ref('/Cases/{caseId}/status').onUpdate(event => {
    const caseId = event.params.caseId;
    const newStatus = event.data.val();
    // If it is an answer, send notification
    if (newStatus == "ANSWERED") {
        console.log('New case answered:', caseId); 
        var getAskerIdPromise = event.data.ref.parent.child('client').once('value')
        var getQuestionTitlePromise = event.data.ref.parent.child('title').once('value')	
        // getAskerIdPromise.then(function(response) {
	Promise.all([getAskerIdPromise, getQuestionTitlePromise]).then(function(response) {	    
            const clientId = extractString(response[0]);
            const message = extractString(response[1]);	    
            const getDeviceTokensPromise = admin.database().ref(`/Users/${clientId}/device`).once('value');
            getDeviceTokensPromise.then(function(response) {
		const deviceId = extractString(response);
		console.log("deviceId : ", deviceId);
                const payload = {
                    notification: {
                        title: 'You have a new answer!',
                        //TODO get actual question title and stick in here.
                        body: `Your question "` + message + `" has been answered.`
                        //TODO upload image uri in database. icon: question.photoURL if it exists, otherwise none
                    }
                };
                return admin.messaging().sendToDevice(deviceId, payload)
                    .then(function(response) {
			console.log("Successfully sent message:", response);
                    })
                    .catch(function(error) {
			console.log("Error sending message:", error);
                    });
            });
        });
    }
    console.log("Finished for status : ", newStatus);
    return 'SUCCESS'
});
