  // // Create and Deploy Your First Cloud Functions
  // // https://firebase.google.com/docs/functions/write-firebase-functions
  //
  // exports.helloWorld = functions.https.onRequest((request, response) => {
  //  response.send("Hello from Firebase!");
  // });
  // The Cloud Functions for Firebase SDK to create Cloud Functions and setup triggers.
  const functions = require('firebase-functions');

  // The Firebase Admin SDK to access the Firebase Realtime Database. 
  const admin = require('firebase-admin');
  admin.initializeApp(functions.config().firebase);

  // Take the text parameter passed to this HTTP endpoint and insert it into the
  // Realtime Database under the path /messages/:pushId/original
  exports.addMessage = functions.https.onRequest((req, res) => {
    // Grab the text parameter.
    const original = req.query.text;
    // Push the new message into the Realtime Database using the Firebase Admin SDK.
    admin.database().ref('/messages').push({original: original}).then(snapshot => {
      // Redirect with 303 SEE OTHER to the URL of the pushed object in the Firebase console.
      res.redirect(303, snapshot.ref);
    });
  });

  /**
   * Triggers when a user gets a new follower and sends a notification.
   *
   * Followers add a flag to `/followers/{followedUid}/{followerUid}`.
   * Users save their device notification tokens to `/users/{followedUid}/notificationTokens/{notificationToken}`.
   */
  exports.sendFollowerNotification = functions.database.ref('/followers/{followedUid}/{followerUid}').onWrite(event => {
    const followerUid = event.params.followerUid;
    const followedUid = event.params.followedUid;
    // If un-follow we exit the function.
    if (!event.data.val()) {
      return console.log('User ', followerUid, 'un-followed user', followedUid);
    }
    console.log('We have a new follower UID:', followerUid, 'for user:', followerUid);

    // Get the list of device notification tokens.
    const getDeviceTokensPromise = admin.database().ref(`/users/${followedUid}/notificationTokens`).once('value');

    // Get the follower profile.
    const getFollowerProfilePromise = admin.auth().getUser(followerUid);

    return Promise.all([getDeviceTokensPromise, getFollowerProfilePromise]).then(results => {
      const tokensSnapshot = results[0];
      const follower = results[1];

      // Check if there are any device tokens.
      if (!tokensSnapshot.hasChildren()) {
        return console.log('There are no notification tokens to send to.');
      }
      console.log('There are', tokensSnapshot.numChildren(), 'tokens to send notifications to.');
      console.log('Fetched follower profile', follower);

      // Notification details.
      const payload = {
        notification: {
          title: 'You have a new follower!',
          body: `${follower.displayName} is now following you.`,
          icon: follower.photoURL
        }
      };

      // Listing all tokens.
      const tokens = Object.keys(tokensSnapshot.val());

      // Send notifications to all tokens.
      return admin.messaging().sendToDevice(tokens, payload).then(response => {
        // For each message check if there was an error.
        const tokensToRemove = [];
        response.results.forEach((result, index) => {
          const error = result.error;
          if (error) {
            console.error('Failure sending notification to', tokens[index], error);
            // Cleanup the tokens who are not registered anymore.
            if (error.code === 'messaging/invalid-registration-token' ||
                error.code === 'messaging/registration-token-not-registered') {
              tokensToRemove.push(tokensSnapshot.ref.child(tokens[index]).remove());
            }
          }
        });
        return Promise.all(tokensToRemove);
      });
    });
  });

  
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
    // If un-follow we exit the function.
    if (newStatus == "ANSWERED") {
     // return console.log('User ', followerUid, 'un-followed user', followedUid);
    
    console.log('New case answered:', caseId);

    
        const path = '/Cases/' + caseId + '/client';
        console.log('path', path);
        
        var getAskerIdPromise = event.data.ref.parent.child('client').once('value')
        getAskerIdPromise.then(function(response) {
          console.log("client : ", response["A"]);
          const r1 = response["A"];
          const r2 = r1["B"];
          const clientId = r2.toString();
          const getDeviceTokensPromise = admin.database().ref(`/Users/${clientId}/device`).once('value');
          getDeviceTokensPromise.then(function(device_token_response) {
            console.log("device token response : ", device_token_response);
            const token_1 = device_token_response["A"];
            const token_2 = token_1["B"];
            const deviceId = token_2.toString();
            console.log("deviceId : ", deviceId);
                const payload = {
                    notification: {
                  title: 'You have a new answer!',
                  body: `Your question has been answered.`
            //        icon: follower.photoURL
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
        //const getAskerIdPromise = admin.database().ref(path).once('value');
    //   console.log('Client:', getAskerIdPromise)
    // Get the list of device notification tokens.
  //  const getDeviceTokensPromise = admin.database().ref(`/users/${followedUid}/notificationTokens`).once('value');

        // Get the follower profile.
      //   const getFollowerProfilePromise = getAskerIdPromise.then(function(clientId) {
      // console.log("Client id : ", clientId);
      
      // const path = '/Users/${clientId}/device';
      // return admin.database().ref(path).once('value');
      //   });                  

      //   return getFollowerProfilePromise.then(deviceId => {
      // if (!deviceId) {
      //   return console.log('There is no device to send to.');
      // }

      // console.log('Send to device: ', deviceId);
      // // Notification details.
      // deviceId = "dKNeEKJ3YFI:APA91bGo7y2_cidKYRxa0OOnKEVOld7n1KDiZuHgjDbzvw-RAlbGJeWGRsdf6vD7x9A8Lfr1xzupGpfp_qmkYoBfBgD7R6JG7priJyrobzT_371V54AUUbAaReY_Pr7Wxjq01pu8K5Fw";

      
    // });
  }
      console.log("Finished for status : ", newStatus);
      return 'SUCCESS'
  });
