package com.rscbyte.spendifylite.models;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

@Table
public class MProfile extends SugarRecord {

    private String names = "";
    private String email = "";
    private String phone = "";
    private String gender = "";
    private String quotes = "";
    private int currency = 0;
    private String country = "Nigeria (NGN)";
    private String symbol = "NGN";
    private int protects = 1;
    private int notifications = 1;
    private int sms = 1;

    //initialize
    public MProfile() {
    }

    //initialize with arguments


    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getQuotes() {
        return quotes;
    }

    public void setQuotes(String quotes) {
        this.quotes = quotes;
    }

    public int getNotifications() {
        return notifications;
    }

    public void setNotifications(int notifications) {
        this.notifications = notifications;
    }

    public int getSms() {
        return sms;
    }

    public void setSms(int sms) {
        this.sms = sms;
    }

    public int getProtects() {
        return protects;
    }

    public void setProtects(int protects) {
        this.protects = protects;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
