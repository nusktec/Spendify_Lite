package com.rscbyte.spendifylite.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.orm.SugarRecord;
import com.orm.util.NamingHelper;
import com.rscbyte.spendifylite.Utils.Constants;
import com.rscbyte.spendifylite.Utils.Tools;
import com.rscbyte.spendifylite.models.BankChecker;
import com.rscbyte.spendifylite.models.MData;
import com.rscbyte.spendifylite.models.MSms;
import com.rscbyte.spendifylite.objects.OAlerts;
import com.rscbyte.spendifylite.objects.OSms;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SMSService extends Service {
    private static final String UBA_BANK = "UBA";
    private static final String ZENITH_BANK = "ZENITH";
    private static final String FIRST_BANK = "FIRST_BANK";
    private static final String ACCESS_BANK = "ACCESS";
    private static final String UNION_BANK = "7011278753";//"UNION";
    private static final String FIDELITY_BANK = "FIDELITY";
    private static final String GT_BANK = "GTBANK";
    private static final String ECO_BANK = "ECOBANK";
    private static final String POLARIS_BANK = "POLARIS";
    private static final String FCMB_BANK = "FCMB";

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
            BankChecker.unionBank(sms, new BankChecker.MoneyBack() {
                @Override
                public void isMoney(Boolean isOkay, OAlerts o) {
                    //call inserting methods
                    if (isOkay) {
                        insertOnly(o);
                    }
                }
            });

        }
        if (sms.getAddress().toUpperCase().contains(FIDELITY_BANK)) {
            //Fidelity Algorithms

        }
        if (sms.getAddress().toUpperCase().contains(POLARIS_BANK)) {
            //Polaris Algorithms

        }
        if (sms.getAddress().toUpperCase().contains(ECO_BANK)) {
            //EcoBank Algorithms

        }
        if (sms.getAddress().toUpperCase().contains(FCMB_BANK)) {
            //FCMB Algorithms

        }
    }

    //filter specifically
    void insertOnly(OAlerts o) {
        String msgID = o.getMsgID();
        long chk = MData.count(MData.class, dbName("trxMsgID") + "=?", new String[]{msgID});
        if (chk > 0) {
            Log.e("Service Db", "Already inserted " + o.getMsgID());
            return;
        }
        //insert in db
        MData data = new MData();
        data.setTrxSTP(Long.parseLong(o.getTimeStp()));
        data.setTrxDate(o.getRawDate());
        data.setTrxValue(o.getMoney());
        data.setTrxDesc(o.getDescr());
        data.setTrxMsgID(o.getMsgID());
        data.setTrxType(o.getMode());
        data.setTrxSrc(2); //for sms
        //set date
        Calendar c = Tools.timeStamp(Long.parseLong(o.getTimeStp()));
        data.setTrxDay(c.get(Calendar.DATE) + "");
        data.setTrxMonth(c.get(Calendar.MONTH) + 1 + "");
        data.setTrxMonth(c.get(Calendar.YEAR) + "");
        //insert into db
        SugarRecord.save(data);
        getBaseContext().getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE).edit()
                .putInt(Constants.SHARED_ALERT_KEY, 1).apply();
        Log.v("Alert Saved", "Inserted " + o.getMsgID());
    }

    protected String dbName(String s) {
        return NamingHelper.toSQLNameDefault(s);
    }
}