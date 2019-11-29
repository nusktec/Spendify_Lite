package com.rscbyte.spendifylite.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.rscbyte.spendifylite.Utils.Tools;
import com.rscbyte.spendifylite.models.BankChecker;
import com.rscbyte.spendifylite.models.MData;
import com.rscbyte.spendifylite.models.MSms;
import com.rscbyte.spendifylite.objects.OAlerts;
import com.rscbyte.spendifylite.objects.OSms;

import java.util.Date;
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
                public void isMoney(Boolean isOkay, OAlerts o) {
                    //call inserting methods
                    if (isOkay) {
                        insertOnly(o);
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
    void insertOnly(OAlerts o) {
        Log.e("Money", o.getMoney());
        Log.e("Date", o.getRawDate());
        Log.e("TimeStp", o.getTimeStp());
        Log.e("Desc", o.getDescr());
        Log.e("MsgID", o.getMsgID());
        String msgID = o.getMsgID();
        long chk = MData.count(MData.class, "WHERE TRX_SMS_ID=?", new String[]{msgID});
        if (chk > 0) {
            Log.e("Db MsgErr", "ALerdy inserted");
            return;
        }
        //insert in db
        MData data = new MData();
        Log.e("Db Msg", "You can insert");
        Date d = Tools.timeStamp(Long.parseLong(o.getTimeStp()));
        Log.e("Data Converted", d.toGMTString());
    }
}
