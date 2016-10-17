package ru.anit.anitfresh.data.services.firebasemessaging;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import ru.anit.anitfresh.Interators.puttoken.InteratorPutToken;
import ru.anit.anitfresh.Interators.synchronizer.InteratorSynhronizerNotNotifi;
import ru.anit.anitfresh.R;
import ru.anit.anitfresh.general.general.LogHelper;
import ru.anit.anitfresh.ui.mainactivity.MainActivity;


/**
 *  notifi
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        LogHelper.d("nitification: " +remoteMessage.getFrom());

        RemoteMessage.Notification notification = remoteMessage.getNotification();

        if (notification != null) {
            //sendNotification(remoteMessage.getNotification().getBody());
        }


        Map<String, String> data = (Map<String, String>) remoteMessage.getData();

        String COMMAND = "command";
        String SYNCHRONIZATION = "synchronization";

        if (data != null) {
            String command = remoteMessage.getData().get("command");
            if(command != null){
                if (command.equalsIgnoreCase(SYNCHRONIZATION)) {
                    new InteratorPutToken().execute();
                    new InteratorSynhronizerNotNotifi().execute();
                }
            }
        }

    }


    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("АНИТ")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}