package com.rscbyte.spendifylite.objects;

public class OAlerts {
    private String money = "0.00";
    private String descr = "No desc";
    private String timeStp = "000000000";
    private String msgID = "0";
    private String rawDate = "";
    private String bankName = "";
    private int mode = 2;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getTimeStp() {
        return timeStp;
    }

    public void setTimeStp(String timeStp) {
        this.timeStp = timeStp;
    }

    public String getMsgID() {
        return msgID;
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getRawDate() {
        return rawDate;
    }
    public void setRawDate(String rawDate) {
        this.rawDate = rawDate;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
