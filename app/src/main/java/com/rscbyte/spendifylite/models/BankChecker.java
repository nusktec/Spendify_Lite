package com.rscbyte.spendifylite.models;

import android.util.Log;

import com.rscbyte.spendifylite.Utils.Tools;
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
            } else if (sms.getMsg().toUpperCase().contains("CREDIT")) {
                oAlerts.setMode(1);
            } else {
                return;
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
            oAlerts.setBankName("UBA");
            moneyBack.isMoney(true, oAlerts);
        } catch (Exception exp) {
            //keep silence
            moneyBack.isMoney(false, null);
            exp.printStackTrace();
        }
    }

    //access bank
    public static void accessBank(OSms sms, MoneyBack moneyBack) {
        //algorithms shuffler
        OAlerts oAlerts = new OAlerts();
        try {
            if (sms.getMsg().toUpperCase().contains("DEBIT")) {
                oAlerts.setMode(2);
            } else if (sms.getMsg().toUpperCase().contains("CREDIT")) {
                oAlerts.setMode(1);
            } else {
                return;
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
                if (i.substring(0, 4).toUpperCase().equals("TIME")) {
                    oAlerts.setRawDate(i.split(":")[1]);
                }
            }
            oAlerts.setMsgID(sms.getId());
            oAlerts.setTimeStp(sms.getTime());
            oAlerts.setBankName("Access Bank");
            moneyBack.isMoney(true, oAlerts);
        } catch (Exception exp) {
            //keep silence
            moneyBack.isMoney(false, null);
            exp.printStackTrace();
        }
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
        try {
            if (sms.getMsg().toUpperCase().contains("DR")) {
                oAlerts.setMode(2);
            } else if (sms.getMsg().toUpperCase().contains("CR")) {
                oAlerts.setMode(1);
            } else {
                return;
            }
            //it's a debit alert
            String[] body = sms.getMsg().split("[\\r?\\n]+");
            for (String i : body) {
                //do each line
                try {
                    if (i.substring(0, 3).toUpperCase().equals("AMT")) {
                        String split[] = (i + " DR PathData").split("DR");
                        String value1 = split[0], value2 = split[1];
                        value1 = value1.replaceAll("[^\\d.]", "");
                        if (Tools.stringContainsNumber(value2)) {
                            value2 = value2.replaceAll("[^\\d.]", "");
                        } else {
                            value2 = "0";
                        }
                        //float them and add up
                        Float money = Float.parseFloat(value1) + Float.parseFloat(value2);
                        oAlerts.setMoney(money + "");
                    }
                } catch (ArrayIndexOutOfBoundsException arr) {
                    //print error
                    arr.printStackTrace();
                }
                if (i.substring(0, 4).toUpperCase().equals("DESC")) {
                    oAlerts.setDescr(i.split(":")[1]);
                }
                if (i.substring(0, 4).toUpperCase().equals("DATE")) {
                    oAlerts.setRawDate(i.split(":")[1]);
                } else {
                    String tmpdate = Tools.timeStampStr(Long.parseLong(sms.getTime()));
                    oAlerts.setRawDate(tmpdate);
                }
            }
            oAlerts.setMsgID(sms.getId());
            oAlerts.setTimeStp(sms.getTime());
            oAlerts.setBankName("GTBank");
            moneyBack.isMoney(true, oAlerts);
        } catch (StringIndexOutOfBoundsException ex) {
            //keep silence
            moneyBack.isMoney(false, null);
            ex.printStackTrace();
        }
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
            } else if (sms.getMsg().toUpperCase().contains("CR ALERT")) {
                oAlerts.setMode(1);
            } else {
                return;
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
            oAlerts.setBankName("Union Bank");
            moneyBack.isMoney(true, oAlerts);
        } catch (StringIndexOutOfBoundsException exp) {
            //keep silence
            moneyBack.isMoney(false, null);
        }
    }

    //union bank
    public static void stanbicBank(OSms sms, MoneyBack moneyBack) {
        //algorithms shuffler
        OAlerts oAlerts = new OAlerts();
        try {
            if (sms.getMsg().toUpperCase().contains("DEBIT")) {
                oAlerts.setMode(2);
            } else if (sms.getMsg().toUpperCase().contains("CREDIT")) {
                oAlerts.setMode(1);
            } else {
                return;
            }
            //it's a debit alert
            String[] body = sms.getMsg().split("[\\r?\\n]+");
            for (String i : body) {
                //do each line
                if (i.substring(0, 5).toUpperCase().equals("DEBIT") || i.substring(0, 6).toUpperCase().equals("CREDIT")) {
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
            oAlerts.setBankName("Stanbic Bank");
            moneyBack.isMoney(true, oAlerts);
        } catch (Exception exp) {
            //keep silence
            moneyBack.isMoney(false, null);
            exp.printStackTrace();
        }
    }

    //zenith bank
    public static void zenitBank(OSms sms, MoneyBack moneyBack) {
        //algorithms shuffler
        OAlerts oAlerts = new OAlerts();
        try {
            if (sms.getMsg().toUpperCase().contains("DEBIT")) {
                oAlerts.setMode(2);
            } else if (sms.getMsg().toUpperCase().contains("CREDIT")) {
                oAlerts.setMode(1);
            } else {
                return;
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
                if (i.substring(0, 4).toUpperCase().equals("TIME")) {
                    oAlerts.setRawDate(i.split(":")[1]);
                }
            }
            oAlerts.setMsgID(sms.getId());
            oAlerts.setTimeStp(sms.getTime());
            oAlerts.setBankName("Access Bank");
            moneyBack.isMoney(true, oAlerts);
        } catch (Exception exp) {
            //keep silence
            moneyBack.isMoney(false, null);
            exp.printStackTrace();
        }
    }

    //General callback interface
    public interface MoneyBack {
        void isMoney(Boolean isOkay, OAlerts oa);
    }
}
