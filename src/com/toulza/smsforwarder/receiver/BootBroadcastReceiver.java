package com.toulza.smsforwarder.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.toulza.smsforwarder.service.SmsService;

/**
 * Created by Pierre on 29/06/2015.
 */
public class BootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startServiceIntent = new Intent(context, SmsService.class);
        context.startService(startServiceIntent);
    }
}
