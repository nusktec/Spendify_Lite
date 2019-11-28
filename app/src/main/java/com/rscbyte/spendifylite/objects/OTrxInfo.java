package com.rscbyte.spendifylite.objects;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class OTrxInfo extends BaseObservable {
    private String desc;
    private String value;
    private String date;
    private String type;
    private String source;

    //constructor
    public OTrxInfo() {
    }

    @Bindable
    public String getDesc() {
        return "Transaction Desc: " + desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
        notifyPropertyChanged(BR.desc);
    }

    @Bindable
    public String getValue() {
        return "Transaction Value: " + value;
    }

    public void setValue(String value) {
        this.value = value;
        notifyPropertyChanged(BR.value);
    }

    @Bindable
    public String getDate() {
        return "Transaction Date: " + date;
    }

    public void setDate(String date) {
        this.date = date;
        notifyPropertyChanged(BR.date);
    }

    @Bindable
    public String getType() {
        return "Transaction Type: " + type;
    }

    public void setType(String type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }

    @Bindable
    public String getSource() {
        return "Transaction Source: " + source;
    }

    public void setSource(String source) {
        this.source = source;
        notifyPropertyChanged(BR.source);
    }
}
