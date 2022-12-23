package com.example.gamecenter;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderNotification extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.cat_20);

        Log.d("DEBUG DB", "onReceive: notification");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"AlarmManager");
        builder.setContentTitle("Game Center");
        builder.setContentText("Hey, It's been a long time since you played the game!");
        builder.setSmallIcon(R.drawable.card_behind);
        builder.setColor(Color.parseColor("#F64A8A"));
        builder.setLargeIcon(bitmap);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

//        builder.setContentIntent(PendingIntent.getActivity(context, 0, intent, 0));

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(2,builder.build());
    }
}
