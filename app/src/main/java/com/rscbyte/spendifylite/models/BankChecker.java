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
                moneyBack.isMoney(false, null);
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
                moneyBack.isMoney(false, null);
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

    //fcmb bank
    public static void fcmbBank(OSms sms, MoneyBack moneyBack) {
        //algorithms shuffler
        OAlerts oAlerts = new OAlerts();
        try {
            if (sms.getMsg().toUpperCase().contains("DR ALERT")) {
                oAlerts.setMode(2);
            } else if (sms.getMsg().toUpperCase().contains("CR ALERT")) {
                oAlerts.setMode(1);
            } else {
                moneyBack.isMoney(false, null);
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
            oAlerts.setBankName("FCMB Bank");
            moneyBack.isMoney(true, oAlerts);
        } catch (Exception exp) {
            //keep silence
            moneyBack.isMoney(false, null);
            exp.printStackTrace();
        }
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
                moneyBack.isMoney(false, null);
            }
            //it's a debit alert
            String[] body = sms.getMsg().split("[\\r?\\n]+");
            if (body[1].contains("DR")) {
                oAlerts.setMode(2);
            } else if (body[1].contains("CR")) {
                oAlerts.setMode(1);
            }
            for (String i : body) {
                //do each line
                try {
                    if (i.substring(0, 3).toUpperCase().equals("AMT")) {
                        String[] split;
                        if (i.contains("DR")) {
                            split = (i + " DR PathData").split("DR");
                        } else {
                            split = (i + " DR PathData").split("CR");
                        }
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
                } catch (NullPointerException arr) {
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
                moneyBack.isMoney(false, null);
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
                moneyBack.isMoney(false, null);
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

    //polaris bank
    public static void polarisBank(OSms sms, MoneyBack moneyBack) {
        //algorithms shuffler
        OAlerts oAlerts = new OAlerts();
        try {
            if (sms.getMsg().toUpperCase().contains("DEBIT")) {
                oAlerts.setMode(2);
            } else if (sms.getMsg().toUpperCase().contains("CREDIT")) {
                oAlerts.setMode(1);
            } else {
                moneyBack.isMoney(false, null);
            }
            //it's a debit alert
            String[] body = sms.getMsg().split("[\\r?\\n]+");
            for (String i : body) {
                //do each line
                if (i.substring(0, 3).toUpperCase().equals("AMT")) {
                    oAlerts.setMoney(i.replaceAll("[^\\d.]", ""));
                }
                if (i.substring(0, 3).toUpperCase().equals("REF")) {
                    oAlerts.setDescr(i.split(":")[1]);
                }
                String tmpdate = Tools.timeStampStr(Long.parseLong(sms.getTime()));
                oAlerts.setRawDate(tmpdate);
            }
            oAlerts.setMsgID(sms.getId());
            oAlerts.setTimeStp(sms.getTime());
            oAlerts.setBankName("Polaris Bank");
            moneyBack.isMoney(true, oAlerts);
        } catch (Exception exp) {
            //keep silence
            moneyBack.isMoney(false, null);
            exp.printStackTrace();
        }
    }

    //zenith bank
    public static void zenithBank(OSms sms, MoneyBack moneyBack) {
        //algorithms shuffler
        OAlerts oAlerts = new OAlerts();
        try {
            if (sms.getMsg().toUpperCase().contains("DR AMT")) {
                oAlerts.setMode(2);
            } else if (sms.getMsg().toUpperCase().contains("CR AMT")) {
                oAlerts.setMode(1);
            } else {
                moneyBack.isMoney(false, null);
            }
            //it's a debit alert
            String[] body = sms.getMsg().split("[\\r?\\n]+");
            for (String i : body) {
                //do each line
                if (i.substring(0, 6).toUpperCase().equals("DR AMT") || i.substring(0, 6).toUpperCase().equals("CR AMT")) {
                    oAlerts.setMoney(i.replaceAll("[^\\d.]", ""));
                }
                if (i.substring(0, 2).toUpperCase().equals("DT")) {
                    oAlerts.setDescr(sms.getMsg().split("Bal")[0]);
                }
                String tmpdate = Tools.timeStampStr(Long.parseLong(sms.getTime()));
                oAlerts.setRawDate(tmpdate);
            }
            oAlerts.setMsgID(sms.getId());
            oAlerts.setTimeStp(sms.getTime());
            oAlerts.setBankName("Zenith Bank");
            moneyBack.isMoney(true, oAlerts);
        } catch (Exception exp) {
            //keep silence
            moneyBack.isMoney(false, null);
            exp.printStackTrace();
        }
    }

    //keystone bank
    public static void keystoneBank(OSms sms, MoneyBack moneyBack) {
        //algorithms shuffler
        OAlerts oAlerts = new OAlerts();
        try {
            if (sms.getMsg().toUpperCase().contains("DEBIT ALERT")) {
                oAlerts.setMode(2);
            } else if (sms.getMsg().toUpperCase().contains("CREDIT ALERT")) {
                oAlerts.setMode(1);
            } else {
                moneyBack.isMoney(false, null);
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
            oAlerts.setBankName("Keystone Bank");
            moneyBack.isMoney(true, oAlerts);
        } catch (Exception exp) {
            //keep silence
            moneyBack.isMoney(false, null);
            exp.printStackTrace();
        }
    }

    //wema bank
    public static void wemaBank(OSms sms, MoneyBack moneyBack) {
        //algorithms shuffler
        OAlerts oAlerts = new OAlerts();
        try {
            if (sms.getMsg().toUpperCase().contains("WEMA DEBIT")) {
                oAlerts.setMode(2);
            } else if (sms.getMsg().toUpperCase().contains("WEMA CREDIT")) {
                oAlerts.setMode(1);
            } else {
                moneyBack.isMoney(false, null);
            }
            //it's a debit alert
            String[] body = sms.getMsg().split("[\\r?\\n]+");
            for (String i : body) {
                //do each line
                if (i.substring(0, 3).toUpperCase().equals("NGN")) {
                    oAlerts.setMoney(i.replaceAll("[^\\d.]", ""));
                }
                if (i.substring(0, 4).toUpperCase().equals("DESC")) {
                    oAlerts.setDescr(i.split(":")[1]);
                }
                String tmpdate = Tools.timeStampStr(Long.parseLong(sms.getTime()));
                oAlerts.setRawDate(tmpdate);
            }
            oAlerts.setMsgID(sms.getId());
            oAlerts.setTimeStp(sms.getTime());
            oAlerts.setBankName("Wema Bank");
            moneyBack.isMoney(true, oAlerts);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //fidelity bank
    public static void fidelityBank(OSms sms, MoneyBack moneyBack) {
        //algorithms shuffler
        OAlerts oAlerts = new OAlerts();
        try {
            if (sms.getMsg().toUpperCase().contains("DR:")) {
                oAlerts.setMode(2);
            } else if (sms.getMsg().toUpperCase().contains("CR:")) {
                oAlerts.setMode(1);
            } else {
                moneyBack.isMoney(false, null);
            }
            //it's a debit alert
            String[] body = sms.getMsg().split("[\\r?\\n]+");
            for (String i : body) {
                //do each line
                if (i.substring(0, 3).toUpperCase().equals("DR:") || i.substring(0, 3).toUpperCase().equals("CR:")) {
                    oAlerts.setMoney(i.replaceAll("[^\\d.]", ""));
                }
                if (i.substring(0, 4).toUpperCase().equals("DESC")) {
                    oAlerts.setDescr(i.split(":")[1]);
                }
                String tmpdate = Tools.timeStampStr(Long.parseLong(sms.getTime()));
                oAlerts.setRawDate(tmpdate);
            }
            oAlerts.setMsgID(sms.getId());
            oAlerts.setTimeStp(sms.getTime());
            oAlerts.setBankName("Fidelity Bank");
            moneyBack.isMoney(true, oAlerts);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //eco bank
    public static void ecoBank(OSms sms, MoneyBack moneyBack) {
        //algorithms shuffler
        OAlerts oAlerts = new OAlerts();
        try {
            if (sms.getMsg().toUpperCase().contains("DEBIT:")) {
                oAlerts.setMode(2);
            } else if (sms.getMsg().toUpperCase().contains("CREDIT:")) {
                oAlerts.setMode(1);
            } else {
                moneyBack.isMoney(false, null);
            }
            //it's a debit alert
            String[] body = sms.getMsg().split("[\\r?\\n]+");
            for (String i : body) {
                //do each line
                if (i.substring(0, 6).toUpperCase().equals("DEBIT:") || i.substring(0, 7).toUpperCase().equals("CREDIT:")) {
                    oAlerts.setMoney(i.replaceAll("[^\\d.]", ""));
                }
                oAlerts.setDescr(sms.getMsg());
                String tmpdate = Tools.timeStampStr(Long.parseLong(sms.getTime()));
                oAlerts.setRawDate(tmpdate);
            }
            oAlerts.setMsgID(sms.getId());
            oAlerts.setTimeStp(sms.getTime());
            oAlerts.setBankName("Eco Bank");
            moneyBack.isMoney(true, oAlerts);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //suntrust bank
    public static void suntrustBank(OSms sms, MoneyBack moneyBack) {
        //algorithms shuffler
        OAlerts oAlerts = new OAlerts();
        try {
            if (sms.getMsg().toUpperCase().contains("DR")) {
                oAlerts.setMode(2);
            } else if (sms.getMsg().toUpperCase().contains("CR")) {
                oAlerts.setMode(1);
            } else {
                moneyBack.isMoney(false, null);
            }
            //it's a debit alert
            String[] body = sms.getMsg().split("[\\r?\\n]+");
            if (body[2].contains("DR")) {
                oAlerts.setMode(2);
            } else if (body[2].contains("CR")) {
                oAlerts.setMode(1);
            }
            for (String i : body) {
                //do each line
                if (i.substring(0, 4).toUpperCase().equals("AMT:")) {
                    oAlerts.setMoney(i.replaceAll("[^\\d.]", ""));
                }
                if (i.substring(0, 4).toUpperCase().equals("DESC")) {
                    oAlerts.setDescr(i.split(":")[1]);
                }
                String tmpdate = Tools.timeStampStr(Long.parseLong(sms.getTime()));
                oAlerts.setRawDate(tmpdate);
            }
            oAlerts.setMsgID(sms.getId());
            oAlerts.setTimeStp(sms.getTime());
            oAlerts.setBankName("SunTrust Bank");
            moneyBack.isMoney(true, oAlerts);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //first bank
    public static void firstBank(OSms sms, MoneyBack moneyBack) {
        //algorithms shuffler
        OAlerts oAlerts = new OAlerts();
        try {
            if (sms.getMsg().toUpperCase().contains("DEBITED WITH")) {
                oAlerts.setMode(2);
            } else if (sms.getMsg().toUpperCase().contains("CREDITED WITH")) {
                oAlerts.setMode(1);
            } else {
                moneyBack.isMoney(false, null);
            }
            String[] raw_body = sms.getMsg().toUpperCase().split("WITH");
            String trxValue = raw_body[1].trim();

            //do each line
            if (trxValue.substring(0, 3).toUpperCase().equals("NGN")) {
                oAlerts.setMoney(trxValue.split(" ")[0].replaceAll("[^\\d.]", ""));
            }
            oAlerts.setDescr(sms.getMsg());
            String tmpdate = Tools.timeStampStr(Long.parseLong(sms.getTime()));
            oAlerts.setRawDate(tmpdate);

            oAlerts.setMsgID(sms.getId());
            oAlerts.setTimeStp(sms.getTime());
            oAlerts.setBankName("First Bank");
            moneyBack.isMoney(true, oAlerts);

        } catch (Exception ex) {
            ex.printStackTrace();
            moneyBack.isMoney(false, null);
        }
    }

    //heritage bank
    public static void heritageBank(OSms sms, MoneyBack moneyBack) {
        //algorithms shuffler
        //algorithms shuffler
        OAlerts oAlerts = new OAlerts();
        try {
            if (sms.getMsg().toUpperCase().contains("DEBIT ALERT")) {
                oAlerts.setMode(2);
            } else if (sms.getMsg().toUpperCase().contains("CREDIT ALERT")) {
                oAlerts.setMode(1);
            } else {
                moneyBack.isMoney(false, null);
            }
            String[] raw_body = sms.getMsg().toUpperCase().split("AMT:");
            String trxValue = raw_body[1].trim();

            //do each line
            if (trxValue.substring(0, 3).toUpperCase().equals("NGN")) {
                oAlerts.setMoney(trxValue.split(" ")[0].replaceAll("[^\\d.]", ""));
            }
            oAlerts.setDescr(sms.getMsg());
            String tmpdate = Tools.timeStampStr(Long.parseLong(sms.getTime()));
            oAlerts.setRawDate(tmpdate);

            oAlerts.setMsgID(sms.getId());
            oAlerts.setTimeStp(sms.getTime());
            oAlerts.setBankName("Heritage Bank");
            moneyBack.isMoney(true, oAlerts);

        } catch (Exception ex) {
            ex.printStackTrace();
            moneyBack.isMoney(false, null);
        }
    }


    /////////////////////INTERNATIONAL BANKS\\\\\\\\\\\\\\\\\\\\\\\\\\

    //temporal bank
    public static void mobileMoney(OSms sms, MoneyBack moneyBack) {
        //algorithms shuffler
        moneyBack.isMoney(false, null);
    }

    //temporal bank
    public static void temBank(OSms sms, MoneyBack moneyBack) {
        //algorithms shuffler
        OAlerts oAlerts = new OAlerts();

        moneyBack.isMoney(true, oAlerts);
    }

    //General callback interface
    public interface MoneyBack {
        void isMoney(Boolean isOkay, OAlerts oa);
    }
}
