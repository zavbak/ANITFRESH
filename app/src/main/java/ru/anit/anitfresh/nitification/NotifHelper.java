package ru.anit.anitfresh.nitification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.IBinder;


import ru.anit.anitfresh.R;
import ru.anit.anitfresh.application.App;
import ru.anit.anitfresh.ui.mainactivity.MainActivity;


/**
 * Created by Александр on 30.09.2016.
 */

public class NotifHelper {

    private static final int NOTIFY_ID = 101;

    public static void sendNotif() {
        // 1-я часть
        Context context = App.getContext();

        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Resources res = context.getResources();
        Notification.Builder builder = new Notification.Builder(context);

        Uri uri=Uri.parse("android.resource://"+App.getContext().getPackageName()+"/raw/gorn");

        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_menu_gallery)
                // большая картинка
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_menu_send))
                //.setTicker(res.getString(R.string.warning)) // текст в строке состояния
                .setTicker("Последнее китайское предупреждение!")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                //.setContentTitle(res.getString(R.string.notifytitle)) // Заголовок уведомления
                .setContentTitle("ANIT SMART")
                //.setContentText(res.getString(R.string.notifytext))
                .setContentText("Новая задача")
                //.setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE|Notification.DEFAULT_LIGHTS)
                .setDefaults(Notification.DEFAULT_VIBRATE|Notification.DEFAULT_LIGHTS)
                //.setProgress(100, 50, false)
                .setSound(uri); //Прогрес бар

         Notification notification = builder.getNotification(); // до API 16
        //Notification notification = builder.build();

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFY_ID, notification);
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }
}
