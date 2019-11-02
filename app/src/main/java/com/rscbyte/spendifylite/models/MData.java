package com.rscbyte.spendifylite.models;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

@Table
public class MData extends SugarRecord {

    private String trxDesc;
    private String trxDate;
    private String trxType;
    private double trxValue;
    private int trxSrc;
    //split year
    private String trxYear;
    private String trxMonth;
    private String trxDay;

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

    public String getTrxType() {
        return trxType;
    }

    public void setTrxType(String trxType) {
        this.trxType = trxType;
    }

    public double getTrxValue() {
        return trxValue;
    }

    public void setTrxValue(double trxValue) {
        this.trxValue = trxValue;
    }

    public int getTrxSrc() {
        return trxSrc;
    }

    public void setTrxSrc(int trxSrc) {
        this.trxSrc = trxSrc;
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
        return trxDay;
    }

    public void setTrxDay(String trxDay) {
        this.trxDay = trxDay;
    }
}
