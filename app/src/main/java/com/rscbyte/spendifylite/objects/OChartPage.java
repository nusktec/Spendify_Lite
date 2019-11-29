package com.rscbyte.spendifylite.objects;

import android.widget.TextView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.rscbyte.spendifylite.R;

public class OChartPage extends BaseObservable {

    private String txtTypical = "0.00";
    private String txtBelowTypical = "0.00";
    private String txtSpentSoFar = "0.00";
    private String txtStatement = "Your monthly remarks";
    private String txtVariesTyical = "Determined";
    private int txtColor = R.color.green_500;

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

    public String getTxtVariesTyical() {
        return txtVariesTyical;
    }

    public void setTxtVariesTyical(String txtVariesTyical) {
        this.txtVariesTyical = txtVariesTyical;
    }

    public int getTxtColor() {
        return txtColor;
    }

    @BindingAdapter({"app:color"})
    public static void setTxtColorBinder(TextView view, int txtColor) {
        view.setTextColor(view.getContext().getResources().getColor(txtColor));
    }

    public void setTxtColor(int txtColor) {
        this.txtColor = txtColor;
    }
}
