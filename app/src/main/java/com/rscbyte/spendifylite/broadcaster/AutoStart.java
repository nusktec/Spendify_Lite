package com.rscbyte.spendifylite.broadcaster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.rscbyte.spendifylite.Utils.Tools;
import com.rscbyte.spendifylite.services.SMSService;

public class AutoStart extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if (Tools.isMyServiceRunning(context, SMSService.class)) {
            context.stopService(new Intent(context, SMSService.class));
        } else {
            context.startService(new Intent(context, SMSService.class));
        }
    }
}
