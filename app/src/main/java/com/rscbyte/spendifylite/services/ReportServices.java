package com.rscbyte.spendifylite.services;

import android.app.AlarmManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class ReportServices extends Service {

    //Good morning Rev....Your total expense is N71,000 as of yesterday! Spend Wisely!

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //start a reminder
        try {
            circleClock();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    //start circle clock
    private void circleClock() {
        
    }

    //give report every morning (6.00am)
    private void fireMorningNotifications() {

    }
}
