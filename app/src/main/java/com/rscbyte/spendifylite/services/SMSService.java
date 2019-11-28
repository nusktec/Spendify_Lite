package com.rscbyte.spendifylite.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.rscbyte.spendifylite.models.MSms;
import com.rscbyte.spendifylite.objects.OSms;

import java.util.Collections;
import java.util.List;

public class SMSService extends Service {
    private static final int UBA_BANK = 1;
    private static final int ZENITH_BANK = 2;

    private Context act;

    public SMSService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.act = getApplicationContext();
        startSMSFiltering();
    }

    //insert sms filtered
    void startSMSFiltering() {
        MSms mSms = new MSms(act);
        List<OSms> oSmsList = mSms.getAllSms();
        Collections.reverse(oSmsList);
        for (OSms ms : oSmsList) {
            checkBankAndInsert(ms);
        }
    }

    //void do banking format and insert
    void checkBankAndInsert(OSms sms) {

    }
}
