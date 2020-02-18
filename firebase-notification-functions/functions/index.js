const functions = require("firebase-functions");
const admin = require("firebase-admin");
admin.initializeApp(functions.config().firebase);

exports.sendNotificationToTopic = functions.firestore
  .document("chats/{uid}")
  .onWrite(async event => {
    var message = {
      notification: {
        title: "Notificacion",
        body: "Se han realizado cambios en la app."
      },
      topic: "general"
    };

    let response = await admin.messaging().send(message);
    console.log(response);
  });
