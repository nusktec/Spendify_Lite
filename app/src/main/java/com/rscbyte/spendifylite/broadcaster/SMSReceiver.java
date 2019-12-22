package com.rscbyte.spendifylite.broadcaster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.rscbyte.spendifylite.Utils.Tools;
import com.rscbyte.spendifylite.activities.Dashboard;
import com.rscbyte.spendifylite.services.SMSService;

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //start service
        if (Tools.isMyServiceRunning(context, SMSService.class)) {
            context.stopService(new Intent(context, SMSService.class));
        } else {
            context.startService(new Intent(context, SMSService.class));
        }
        Tools.Notification(context, "New Bank Alert", "SMS Synchronized", 20 + " Alert(s) were synchronized just now, tap to view", 1, Dashboard.class, "No Data");

    }
}
