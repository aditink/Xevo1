
var admin = require("firebase-admin");

var serviceAccount = require("./xevo1-3358a-firebase-adminsdk-rlja2-99f0342893.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://xevo1-3358a.firebaseio.com"
});

var registrationToken = "dKNeEKJ3YFI:APA91bGo7y2_cidKYRxa0OOnKEVOld7n1KDiZuHgjDbzvw-RAlbGJeWGRsdf6vD7x9A8Lfr1xzupGpfp_qmkYoBfBgD7R6JG7priJyrobzT_371V54AUUbAaReY_Pr7Wxjq01pu8K5Fw";

var payload = {
  notification: {
    title: "This is a Notification",
    body: "This is the body of the notification message."
  }
};

 var options = {
  priority: "high",
  timeToLive: 60 * 60 *24
};

admin.messaging().sendToDevice(registrationToken, payload, options)
  .then(function(response) {
    console.log("Successfully sent message:", response);
  })
  .catch(function(error) {
    console.log("Error sending message:", error);
  });