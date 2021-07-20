import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseMessagingSender {
    public String registrationToken;
    public Message message;

    /**
     * Initialize the app
     * */
    public void initializeFirebaseApp() {
        try {

            //get private key
            FileInputStream serviceAccount =
                    new FileInputStream("C:/Users/SHUBHAM SAXENA/Desktop/Web Dev/Headway Consulting/Day 1 PWA/poc-of-pwa2-firebase-adminsdk-cmqsf-0834bb028e.json");

            //Configure options and credentials to initialize your firebase app
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();

            //initialize app
            FirebaseApp.initializeApp(options);
            System.out.println("Initialized App");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create message
     * */
    public void setMessage(String registrationToken) {
        // This registration token comes from the client FCM SDKs.
        this.registrationToken = registrationToken;


        //Make a notification object and use it to make a notification message
        Notification notification = Notification.builder()
                .setTitle("")
                .setBody("")
                .setImage("").build();

        //build a message object, use putData() for providing key and value pairs for your data message
        //use setNotification() to send a notification message
        this.message = Message.builder()
                //.setNotification(notification)
                .putData("dataTitle", "My Github Profile")
                .putData("dataBody", "Hello, Click here to go to my github profile")
                .putData("dataImage", "https://images.pexels.com/photos/674010/pexels-photo-674010.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500")
                .putData("url", "https://github.com/shubhamsaxena924")
                .setToken(this.registrationToken)
                .build();
    }

    /**
     * Send message
     * */
    public void sendMessage() {
        // Send a message to the device corresponding to the provided
        // registration token.
        try {
            String response = FirebaseMessaging.getInstance().send(this.message);
            // Response is a message ID string.
            System.out.println("Successfully sent message: " + response);
        } catch (FirebaseMessagingException e) {
            System.out.println("Message not sent");
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        FirebaseMessagingSender sender = new FirebaseMessagingSender();
        sender.initializeFirebaseApp();

        String FCMToken = "dp05FGILR_2YWf1fLjWmi-:APA91bE-_r4Rtot2zP1T8pFJVlO456m6PypgJcySGxMGriLD8jGPrhcndo5NPc0K4AftUdxaqvPt-ll7JNjqNLI0n2erdP3zdvDL3r6ktpemAzJGEdzt9GVrVy9eW78vglFqbgXrWVv0";
        sender.setMessage(FCMToken);
        sender.sendMessage();
    }
}
