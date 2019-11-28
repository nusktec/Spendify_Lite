package com.rscbyte.spendifylite.objects;

import androidx.databinding.BaseObservable;

import com.rscbyte.spendifylite.BR;

public class OChartPage extends BaseObservable {

    private String txtTypical = "0.00";
    private String txtBelowTypical = "0.00";
    private String txtSpentSoFar = "0.00";
    private String txtStatement = "Your monthly remarks";

    public OChartPage() {
    }

    public String getTxtTypical() {
        return txtTypical;
    }

    public void setTxtTypical(String txtTypical) {
        this.txtTypical = txtTypical;
    }

    public String getTxtBelowTypical() {
        return txtBelowTypical;
    }

    public void setTxtBelowTypical(String txtBelowTypical) {
        this.txtBelowTypical = txtBelowTypical;
    }

    public String getTxtSpentSoFar() {
        return txtSpentSoFar;
    }

    public void setTxtSpentSoFar(String txtSpentSoFar) {
        this.txtSpentSoFar = txtSpentSoFar;
    }

    public String getTxtStatement() {
        return txtStatement;
    }

    public void setTxtStatement(String txtStatement) {
        this.txtStatement = txtStatement;
    }
}
