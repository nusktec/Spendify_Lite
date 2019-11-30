package com.rscbyte.spendifylite.models;

import android.util.Log;

import com.rscbyte.spendifylite.objects.OAlerts;
import com.rscbyte.spendifylite.objects.OSms;

public class BankChecker {

    protected static int MODE_CREDIT = 1;
    protected static int MODE_DEBIT = 2;

    //uba bank
    public static void ubaBank(OSms sms, MoneyBack moneyBack) {
        //algorithms shuffler
        OAlerts oAlerts = new OAlerts();
        try {
            if (sms.getMsg().toUpperCase().contains("DEBIT")) {
                oAlerts.setMode(2);
            } else {
                oAlerts.setMode(1);
            }
            //it's a debit alert
            String[] body = sms.getMsg().split("[\\r?\\n]+");
            for (String i : body) {
                //do each line
                if (i.substring(0, 3).toUpperCase().equals("AMT")) {
                    oAlerts.setMoney(i.replaceAll("[^\\d.]", ""));
                }
                if (i.substring(0, 4).toUpperCase().equals("DESC")) {
                    oAlerts.setDescr(i.split(":")[1]);
                }
                if (i.substring(0, 4).toUpperCase().equals("DATE")) {
                    oAlerts.setRawDate(i.split(":")[1]);
                }
            }
            oAlerts.setMsgID(sms.getId());
            oAlerts.setTimeStp(sms.getTime());
            moneyBack.isMoney(true, oAlerts);
        } catch (StringIndexOutOfBoundsException exp) {
            //keep silence
            moneyBack.isMoney(false, null);
        }
    }

    //access bank
    public static void accessBank(OSms sms, MoneyBack moneyBack) {
        //algorithms shuffler
        OAlerts oAlerts = new OAlerts();

        moneyBack.isMoney(true, oAlerts);
    }

    //first bank
    public static void firstBank(OSms sms, MoneyBack moneyBack) {
        //algorithms shuffler
        OAlerts oAlerts = new OAlerts();

        moneyBack.isMoney(true, oAlerts);
    }

    //gt bank
    public static void gtBank(OSms sms, MoneyBack moneyBack) {
        //algorithms shuffler
        OAlerts oAlerts = new OAlerts();

        moneyBack.isMoney(true, oAlerts);
    }

    //fidelity bank
    public static void fidelityBank(OSms sms, MoneyBack moneyBack) {
        //algorithms shuffler
        OAlerts oAlerts = new OAlerts();

        moneyBack.isMoney(true, oAlerts);
    }

    //union bank
    public static void unionBank(OSms sms, MoneyBack moneyBack) {
        //algorithms shuffler
        OAlerts oAlerts = new OAlerts();
        try {
            if (sms.getMsg().toUpperCase().contains("DR ALERT")) {
                oAlerts.setMode(2);
            } else {
                oAlerts.setMode(1);
            }
            //it's a debit alert
            String[] body = sms.getMsg().split("[\\r?\\n]+");
            for (String i : body) {
                //do each line
                if (i.substring(0, 3).toUpperCase().equals("AMT")) {
                    oAlerts.setMoney(i.replaceAll("[^\\d.]", ""));
                }
                if (i.substring(0, 4).toUpperCase().equals("DESC")) {
                    oAlerts.setDescr(i.split(":")[1]);
                }
                if (i.substring(0, 4).toUpperCase().equals("DATE")) {
                    oAlerts.setRawDate(i.split(":")[1]);
                }
            }
            oAlerts.setMsgID(sms.getId());
            oAlerts.setTimeStp(sms.getTime());
            moneyBack.isMoney(true, oAlerts);
        } catch (StringIndexOutOfBoundsException exp) {
            //keep silence
            moneyBack.isMoney(false, null);
        }
    }

    //General callback
    public interface MoneyBack {
        void isMoney(Boolean isOkay, OAlerts oa);
    }
}
