package com.example.phmst72021;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MedAlarmReciever extends BroadcastReceiver {
   String medName, message;

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle extras = intent.getExtras();
        medName = extras.getString("MedName");
        message = extras.getString("Message");

        NotifyMedAlarm notifyMedAlarm = new NotifyMedAlarm(context);
        NotificationCompat.Builder notifyBuilder = notifyMedAlarm.getChannelNotification("PHMS Medication Alarm!","Take 10 mL of Tylenol");
        notifyMedAlarm.getManager().notify(1, notifyBuilder.build());
    }

}
