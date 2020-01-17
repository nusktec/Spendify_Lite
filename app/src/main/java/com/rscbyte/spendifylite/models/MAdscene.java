package com.rscbyte.spendifylite.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MAdscene {

    @SerializedName("aid")
    @Expose
    private String aid;
    @SerializedName("aurl")
    @Expose
    private String aurl;
    @SerializedName("acompany")
    @Expose
    private String acompany;
    @SerializedName("atime")
    @Expose
    private String atime;
    @SerializedName("aviews")
    @Expose
    private String aviews;
    @SerializedName("astatus")
    @Expose
    private String astatus;
    @SerializedName("adate")
    @Expose
    private String adate;

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getAurl() {
        return aurl;
    }

    public void setAurl(String aurl) {
        this.aurl = aurl;
    }

    public String getAcompany() {
        return acompany;
    }

    public void setAcompany(String acompany) {
        this.acompany = acompany;
    }

    public String getAtime() {
        return atime;
    }

    public void setAtime(String atime) {
        this.atime = atime;
    }

    public String getAviews() {
        return aviews;
    }

    public void setAviews(String aviews) {
        this.aviews = aviews;
    }

    public String getAstatus() {
        return astatus;
    }

    public void setAstatus(String astatus) {
        this.astatus = astatus;
    }

    public String getAdate() {
        return adate;
    }

    public void setAdate(String adate) {
        this.adate = adate;
    }

}