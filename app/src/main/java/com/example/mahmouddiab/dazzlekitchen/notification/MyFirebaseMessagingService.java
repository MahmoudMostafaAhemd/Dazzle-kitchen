package com.example.mahmouddiab.dazzlekitchen.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.mahmouddiab.dazzlekitchen.MainActivity;
import com.example.mahmouddiab.dazzlekitchen.R;
import com.example.mahmouddiab.dazzlekitchen.SplashActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    public void onMessageReceived(RemoteMessage remoteMessage) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("From: ");
        stringBuilder.append(remoteMessage.getFrom());
        Log.d(str, stringBuilder.toString());
        if (remoteMessage.getData().size() > 0) {// type 1 open notification page, title
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Message data payload: ");
            stringBuilder.append(remoteMessage.getData());
            Log.d(str, stringBuilder.toString());
            sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("type"));
        }
        if (remoteMessage.getNotification() != null) {
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Message Notification Body: ");
            stringBuilder.append(remoteMessage.getNotification().getBody());
            Log.d(str, stringBuilder.toString());
        }
    }

    public void onNewToken(String token) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Refreshed token: ");
        stringBuilder.append(token);
        Log.d(str, stringBuilder.toString());
        sendRegistrationToServer(token);
    }

    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    private void sendRegistrationToServer(String token) {
    }

    private void sendNotification(String messageBody, String type) {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("type", type);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}