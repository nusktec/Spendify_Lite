package com.rscbyte.spendifylite.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

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
        return START_STICKY;
    }

    //start circle clock
    private void circleClock() {
        Calendar date6am = Calendar.getInstance();
        date6am.set(Calendar.HOUR_OF_DAY, 18);
        date6am.set(Calendar.MINUTE, 0);

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

            }
        }, date6am.getTime(), 86400000);
    }

    //give report every morning (6.00am)
    private void fireMorningNotifications() {
        
    }
}
