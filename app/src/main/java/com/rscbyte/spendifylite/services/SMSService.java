package com.rscbyte.spendifylite.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.rscbyte.spendifylite.models.BankChecker;
import com.rscbyte.spendifylite.models.MSms;
import com.rscbyte.spendifylite.objects.OSms;

import java.util.List;

public class SMSService extends Service {
    private static final String UBA_BANK = "8164242320"; //"UBA";
    private static final String ZENITH_BANK = "ZENITH";
    private static final String FIRST_BANK = "FIRST_BANK";
    private static final String ACCESS_BANK = "ACCESS";
    private static final String UNION_BANK = "UNION";
    private static final String FIDELITY_BANK = "FIDELITY";
    private static final String GT_BANK = "GTBank";

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
        for (OSms ms : oSmsList) {
            try {
                checkBankAndInsert(ms);
            } catch (NullPointerException npe) {
                //keep silence
                //Log.e("No Data", "One data escaped...");
            }
        }
    }

    //void do banking format and insert
    void checkBankAndInsert(OSms sms) {
        if (sms.getAddress().toUpperCase().contains(UBA_BANK)) {
            //Uba Algorithms
            BankChecker.ubaBank(sms, new BankChecker.MoneyBack() {
                @Override
                public void isMoney(Boolean isOkay, String moneyValue, OSms s) {
                    //call inserting methods
                    if (isOkay) {
                        insertOnly(moneyValue, s);
                    }
                }
            });
        }
        if (sms.getAddress().toUpperCase().contains(GT_BANK)) {
            //GTBank Algorithms

        }
        if (sms.getAddress().toUpperCase().contains(FIRST_BANK)) {
            //First bank Algorithms

        }
        if (sms.getAddress().toUpperCase().contains(ZENITH_BANK)) {
            //Zenith Bank

        }
        if (sms.getAddress().toUpperCase().contains(ACCESS_BANK)) {
            //Access Algorithms

        }
        if (sms.getAddress().toUpperCase().contains(UNION_BANK)) {
            //Union Algorithms

        }
        if (sms.getAddress().toUpperCase().contains(FIDELITY_BANK)) {
            //Fidelity Algorithms

        }
    }

    //filter specifically
    void insertOnly(String money, OSms sms) {
        Log.e("Errrrrrr", sms.getMsg());
    }
}
