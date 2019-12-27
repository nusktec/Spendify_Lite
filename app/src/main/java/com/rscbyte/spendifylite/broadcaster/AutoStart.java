package com.rscbyte.spendifylite.broadcaster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.rscbyte.spendifylite.services.ReportServices;
import com.rscbyte.spendifylite.services.SMSService;

public class AutoStart extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, SMSService.class)); //recheck sms
        context.startService(new Intent(context, ReportServices.class)); //every morning reports
    }
}


