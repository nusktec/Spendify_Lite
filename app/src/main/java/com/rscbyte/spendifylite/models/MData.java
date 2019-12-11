package com.rscbyte.spendifylite.models;

import com.orm.SugarRecord;
import com.orm.dsl.Table;
import com.orm.dsl.Unique;

@Table
public class MData extends SugarRecord {

    private String trxDesc;
    private String trxDate;
    private int trxType;
    private int trxSrc;
    private String trxValue;
    //split year
    private String trxYear;
    private String trxMonth;
    private long trxDay;
    private long trxSTP;
    private String trxBankName;
    @Unique
    private String trxMsgID;

    //initialize the main app
    public MData() {
    }

    //start get and setter

    public String getTrxDesc() {
        return trxDesc;
    }

    public void setTrxDesc(String trxDesc) {
        this.trxDesc = trxDesc;
    }

    public String getTrxDate() {
        return trxDate;
    }

    public void setTrxDate(String trxDate) {
        this.trxDate = trxDate;
    }

    public int getTrxType() {
        return trxType;
    }

    public void setTrxType(int trxType) {
        this.trxType = trxType;
    }

    public int getTrxSrc() {
        return trxSrc;
    }

    public void setTrxSrc(int trxSrc) {
        this.trxSrc = trxSrc;
    }

    public String getTrxValue() {
        return this.trxValue;
    }

    public void setTrxValue(String trxValue) {
        this.trxValue = trxValue;
    }

    public String getTrxYear() {
        return trxYear;
    }

    public void setTrxYear(String trxYear) {
        this.trxYear = trxYear;
    }

    public String getTrxMonth() {
        return trxMonth;
    }

    public void setTrxMonth(String trxMonth) {
        this.trxMonth = trxMonth;
    }

    public String getTrxDay() {
        return String.valueOf(trxDay);
    }

    public void setTrxDay(String trxDay) {
        this.trxDay = Integer.parseInt(trxDay);
    }

    public long getTrxSTP() {
        return trxSTP;
    }

    public void setTrxSTP(long trxSTP) {
        this.trxSTP = trxSTP;
    }

    public String getTrxMsgID() {
        return trxMsgID;
    }

    public void setTrxMsgID(String trxMsgID) {
        this.trxMsgID = trxMsgID;
    }

    public String getTrxBankName() {
        return trxBankName;
    }

    public void setTrxBankName(String trxBankName) {
        this.trxBankName = trxBankName;
    }
}
