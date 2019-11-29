package com.rscbyte.spendifylite.models;

import com.rscbyte.spendifylite.objects.OSms;

public class BankChecker {

    protected static int MODE_CREDIT = 1;
    protected static int MODE_DEBIT = 2;

    //uba bank
    public static void ubaBank(OSms sms, MoneyBack moneyBack) {
        //algorithms shuffler
        if (sms.getMsg().toUpperCase().contains("DEBIT")) {
            //it's a debit alert

            moneyBack.isMoney(true, 2, "00.00", sms);
        } else {
            //it's a credit alert

            moneyBack.isMoney(true, 1, "00.00", sms);
        }
    }

    //access bank
    public static void accessBank(OSms sms, MoneyBack moneyBack) {
        //algorithms shuffler

        moneyBack.isMoney(true, 2, "00.00", sms);
    }

    //first bank
    public static void firstBank(OSms sms, MoneyBack moneyBack) {
        //algorithms shuffler

        moneyBack.isMoney(true, 2, "00.00", sms);
    }

    //gt bank
    public static void gtBank(OSms sms, MoneyBack moneyBack) {
        //algorithms shuffler

        moneyBack.isMoney(true, 2, "00.00", sms);
    }

    //fidelity bank
    public static void fidelityBank(OSms sms, MoneyBack moneyBack) {
        //algorithms shuffler

        moneyBack.isMoney(true, 2, "00.00", sms);
    }

    //union bank
    public static void unionBank(OSms sms, MoneyBack moneyBack) {
        //algorithms shuffler

        moneyBack.isMoney(true, 2, "00.00", sms);
    }

    //General callback
    public interface MoneyBack {
        void isMoney(Boolean isOkay, int mode, String moneyValue, OSms s);
    }
}
