package com.toulza.smsforwarder.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;

import com.toulza.smsforwarder.R;
import com.toulza.smsforwarder.activity.Smslist;

/**
 * Created by Pierre on 25/06/2015.
 */
public class SmsService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        Notification notif = GetNotification("En attente de SMS",new Intent(this, Smslist.class));
        startForeground(R.string.PermanentNotification,notif);
        return null;
    }


    public NotificationManager manager;
    public Boolean isAlreadyOnForeground = false;

    public Notification GetNotification(String text, Intent activity)
    {
        Notification notif;
        if(manager!=null)
        {
            Intent resultIntent = new Intent(this, Smslist.class);
            PendingIntent resultPendingIntent = PendingIntent.getActivity(
                    this,
                    0,
                    resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
            Bitmap bm = BitmapFactory.decodeResource(this.getResources(), R.mipmap.notify_icon);
            notif = new Notification.Builder(this)
                    .setContentTitle(getString(R.string.app_name))
                    .setLargeIcon(bm)
                    .setContentText(text)
                    .setSmallIcon(R.mipmap.notify_icon)
                    .setContentIntent(resultPendingIntent)
                    .getNotification();
            return notif;
        }
        else return null;
    }
}
