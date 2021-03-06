package com.rscbyte.spendifylite.services;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;

import com.orm.SugarContext;
import com.orm.SugarRecord;
import com.orm.util.NamingHelper;
import com.rscbyte.spendifylite.Interfaces.CallBacksArgs;
import com.rscbyte.spendifylite.Utils.Constants;
import com.rscbyte.spendifylite.Utils.Tools;
import com.rscbyte.spendifylite.activities.Dashboard;
import com.rscbyte.spendifylite.models.BankChecker;
import com.rscbyte.spendifylite.models.MData;
import com.rscbyte.spendifylite.models.MProfile;
import com.rscbyte.spendifylite.models.MSms;
import com.rscbyte.spendifylite.objects.OAlerts;
import com.rscbyte.spendifylite.objects.OSms;

import java.util.Calendar;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SMSFunctions {
    private static final String UBA_BANK = "UBA";
    private static final String ACCESS_BANK = "ACCESS";
    private static final String UNION_BANK = "UNION";
    private static final String GT_BANK = "GTBANK";
    private static final String POLARIS_BANK = "POLARIS";
    private static final String FCMB_BANK = "FCMB";
    private static final String STANBIC_BANK = "STANBIC";
    private static final String ZENITH_BANK = "ZENITH";
    private static final String KEYSTONE_BANK = "KEYSTONE";
    private static final String FIRST_BANK = "FIRSTBANK";
    private static final String ECO_BANK = "ECOBANK";
    private static final String FIDELITY_BANK = "FIDELITY";
    private static final String HERITAGE_BANK = "HERITAGE";
    private static final String WEMA_BANK = "WEMA";
    private static final String MOBILEMONEY = "MOBILEMONEY-TEMP";
    private static final String SUN_TRUST_BANK = "SUNTRUST";
    private static final String UNITY_BANK = "UNITYBANK";
    private static final String JAIZ_BANK = "JAIZ";
    private static final String STERLING_BANK = "STERLING";

    private Activity act;
    private MProfile mProfile;

    //callback listener
    private CallBacksArgs callBacks;

    public SMSFunctions(Activity context, CallBacksArgs callBacks) {
        this.callBacks = callBacks;
        this.act = context;
        //run your app
        runSMS();
    }

    //fire sms functions
    private void runSMS() {
        SugarContext.init(act);
        //check user settings
        tester();
        MProfile chkUser = MProfile.findById(MProfile.class, 1);
        try {
            if (chkUser == null) {
                startSMSFiltering();
            } else {
                //check sync. settings
                MProfile data = MProfile.findById(MProfile.class, 1);
                if (data != null) {
                    mProfile = data;
                }
                if ((data != null ? data.getSms() : 0) == 1) {
                    startSMSFiltering();
                } else {
                    //sms check up is off
                    callBacks.onOkay(3);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    //insert sms filtered
    private void startSMSFiltering() {
        MSms mSms = new MSms(act);
        List<OSms> oSmsList = mSms.getAllSms();
        for (OSms ms : oSmsList) {
            try {
                checkBankAndInsert(ms);
            } catch (Exception npe) {
                //keep silence
                //Log.e("No Data", "One data escaped...");
                npe.printStackTrace();
            }
        }
        //synchronizing is completed
        callBacks.onOkay(2);
        //Fire notifications
        if (_counter > 0) {
            if (mProfile == null || mProfile.getNotifications() == 1) {
                Tools.Notification(act, "New Analysis", "Transactions Synchronized", _counter + " Transactions synchronized seconds ago, tap to view", 1, Dashboard.class, "No Data");
            }
        }
    }

    //void do banking format and insert
    void checkBankAndInsert(OSms sms) {
        if (sms.getAddress() == null) {
            return;
        }
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
            BankChecker.gtBank(sms, new BankChecker.MoneyBack() {
                @Override
                public void isMoney(Boolean isOkay, OAlerts o) {
                    //call inserting methods
                    if (isOkay) {
                        insertOnly(o);
                    }
                }
            });
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
        if (sms.getAddress().toUpperCase().contains(STANBIC_BANK)) {
            //Stanbic Algorithms
            BankChecker.stanbicBank(sms, new BankChecker.MoneyBack() {
                @Override
                public void isMoney(Boolean isOkay, OAlerts o) {
                    //call inserting methods
                    if (isOkay) {
                        insertOnly(o);
                    }
                }
            });
        }
        if (sms.getAddress().toUpperCase().contains(ACCESS_BANK)) {
            //Access Algorithms
            BankChecker.accessBank(sms, new BankChecker.MoneyBack() {
                @Override
                public void isMoney(Boolean isOkay, OAlerts o) {
                    //call inserting methods
                    if (isOkay) {
                        insertOnly(o);
                    }
                }
            });
        }
        if (sms.getAddress().toUpperCase().contains(FCMB_BANK)) {
            //FCMB Algorithms
            BankChecker.fcmbBank(sms, new BankChecker.MoneyBack() {
                @Override
                public void isMoney(Boolean isOkay, OAlerts o) {
                    //call inserting methods
                    if (isOkay) {
                        insertOnly(o);
                    }
                }
            });
        }
        if (sms.getAddress().toUpperCase().contains(POLARIS_BANK)) {
            //Polaris Algorithms
            BankChecker.polarisBank(sms, new BankChecker.MoneyBack() {
                @Override
                public void isMoney(Boolean isOkay, OAlerts o) {
                    //call inserting methods
                    if (isOkay) {
                        insertOnly(o);
                    }
                }
            });
        }
        if (sms.getAddress().toUpperCase().contains(ZENITH_BANK)) {
            //Zenith Bank
            BankChecker.zenithBank(sms, new BankChecker.MoneyBack() {
                @Override
                public void isMoney(Boolean isOkay, OAlerts o) {
                    //call inserting methods
                    if (isOkay) {
                        insertOnly(o);
                    }
                }
            });
        }
        if (sms.getAddress().toUpperCase().contains(KEYSTONE_BANK)) {
            //Kyestone bank Algorithms
            BankChecker.keystoneBank(sms, new BankChecker.MoneyBack() {
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
            BankChecker.fidelityBank(sms, new BankChecker.MoneyBack() {
                @Override
                public void isMoney(Boolean isOkay, OAlerts o) {
                    //call inserting methods
                    if (isOkay) {
                        insertOnly(o);
                    }
                }
            });
        }
        if (sms.getAddress().toUpperCase().contains(ECO_BANK)) {
            //EcoBank Algorithms
            BankChecker.ecoBank(sms, new BankChecker.MoneyBack() {
                @Override
                public void isMoney(Boolean isOkay, OAlerts o) {
                    //call inserting methods
                    if (isOkay) {
                        insertOnly(o);
                    }
                }
            });
        }
        if (sms.getAddress().toUpperCase().contains(WEMA_BANK)) {
            //Wema bank Algorithms
            BankChecker.wemaBank(sms, new BankChecker.MoneyBack() {
                @Override
                public void isMoney(Boolean isOkay, OAlerts o) {
                    //call inserting methods
                    if (isOkay) {
                        insertOnly(o);
                    }
                }
            });
        }
        if (sms.getAddress().toUpperCase().contains(SUN_TRUST_BANK)) {
            //Sun bank Algorithms
            BankChecker.suntrustBank(sms, new BankChecker.MoneyBack() {
                @Override
                public void isMoney(Boolean isOkay, OAlerts o) {
                    //call inserting methods
                    if (isOkay) {
                        insertOnly(o);
                    }
                }
            });
        }
        if (sms.getAddress().toUpperCase().contains(FIRST_BANK)) {
            //First bank Algorithms
            BankChecker.firstBank(sms, new BankChecker.MoneyBack() {
                @Override
                public void isMoney(Boolean isOkay, OAlerts o) {
                    //call inserting methods
                    if (isOkay) {
                        insertOnly(o);
                    }
                }
            });
        }
        if (sms.getAddress().toUpperCase().contains(HERITAGE_BANK)) {
            //Heritage Algorithms
            BankChecker.heritageBank(sms, new BankChecker.MoneyBack() {
                @Override
                public void isMoney(Boolean isOkay, OAlerts o) {
                    //call inserting methods
                    if (isOkay) {
                        insertOnly(o);
                    }
                }
            });
        }
        if (sms.getAddress().toUpperCase().contains(MOBILEMONEY)) {
            //Mobile money bank Algorithms
            BankChecker.mobileMoney(sms, new BankChecker.MoneyBack() {
                @Override
                public void isMoney(Boolean isOkay, OAlerts o) {
                    //call inserting methods
                    if (isOkay) {
                        insertOnly(o);
                    }
                }
            });
        }
        if (sms.getAddress().toUpperCase().contains(UNITY_BANK)) {
            //Mobile money bank Algorithms
            BankChecker.unityBank(sms, new BankChecker.MoneyBack() {
                @Override
                public void isMoney(Boolean isOkay, OAlerts o) {
                    //call inserting methods
                    if (isOkay) {
                        insertOnly(o);
                    }
                }
            });
        }
        if (sms.getAddress().toUpperCase().contains(JAIZ_BANK)) {
            //Jaiz bank Algorithms
            BankChecker.jaizBank(sms, new BankChecker.MoneyBack() {
                @Override
                public void isMoney(Boolean isOkay, OAlerts o) {
                    //call inserting methods
                    if (isOkay) {
                        insertOnly(o);
                    }
                }
            });
        }
        if (sms.getAddress().toUpperCase().contains(STERLING_BANK)) {
            //Jaiz bank Algorithms
            BankChecker.sterlingBank(sms, new BankChecker.MoneyBack() {
                @Override
                public void isMoney(Boolean isOkay, OAlerts o) {
                    //call inserting methods
                    if (isOkay) {
                        insertOnly(o);
                    }
                }
            });
        }
    }

    private int _counter = 0;

    //filter specifically
    private void insertOnly(OAlerts o) {
        try {
            //Check inbound for null
            if (o.getBankName().equals("") ||
                    o.getMoney().equals("") ||
                    o.getDescr().equals("") ||
                    o.getMsgID().equals("") ||
                    o.getTimeStp().equals("") ||
                    o.getRawDate().equals("")) {
                //not a valid alert
                return;
            }
            //proceed to data computations
            String msgID = o.getMsgID();
            long chk = MData.count(MData.class, dbName("trxMsgID") + "=?", new String[]{msgID});
            if (chk > 0) {
                //Log.e("Service Db", "Already inserted " + o.getMsgID());
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
            data.setTrxBankName(o.getBankName());
            //set date
            Calendar c = Tools.timeStamp(Long.parseLong(o.getTimeStp()));
            data.setTrxDay(c.get(Calendar.DATE) + "");
            data.setTrxMonth(c.get(Calendar.MONTH) + 1 + "");
            data.setTrxYear(c.get(Calendar.YEAR) + "");
            //insert into db
            SugarRecord.save(data);
            _counter++;
            act.getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE).edit()
                    .putInt(Constants.SHARED_ALERT_KEY, _counter).apply();
            //Log.e("Alert Saved", "Inserted " + o.getMsgID() + " : " + o.getBankName());
        } catch (Exception ex) {
            ex.printStackTrace();
            Tools.Notification(act, "Error Bank Alert", "SMS Synchronized", "Error encounter with bad sms alert integrity", 2, Dashboard.class, "No Data");
        }
    }

    private String dbName(String s) {
        return NamingHelper.toSQLNameDefault(s);
    }


    //Completed {UBA, UNION, GTBank, Stanbic Bank, Access Bank, Polaris, FCMB, Zenith, Keystone, Fidelity, Heritage, Wema, SunTrust, Eco Bank, FBN, Mobile Money}


    private void tester() {

    }
}
