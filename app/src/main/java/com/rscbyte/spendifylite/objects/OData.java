package com.rscbyte.spendifylite.objects;

import com.rscbyte.spendifylite.models.MData;

public class OData {

    //exposed variables
    private String title;
    private String desc;
    private String value;
    private String day;
    private String month;
    private String year;
    private MData mData;

    //initialize all
    public OData() {
    }

    //allow getter and setter
    public OData(String title, String desc, String value, String day, String month, String year, MData mData) {
        this.title = title;
        this.desc = desc;
        this.value = value;
        this.day = day;
        this.month = month;
        this.year = year;
        this.mData = mData;
    }

    //single getter and setter

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public MData getmData() {
        return mData;
    }

    public void setmData(MData mData) {
        this.mData = mData;
    }
}
