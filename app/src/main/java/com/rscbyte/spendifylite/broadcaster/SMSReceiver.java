package com.rscbyte.spendifylite.broadcaster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.rscbyte.spendifylite.services.SMSService;

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //start service
        context.stopService(new Intent(context, SMSService.class));
    }
}
