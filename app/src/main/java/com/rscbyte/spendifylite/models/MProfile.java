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
    private String passcode = "";
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

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }
}
