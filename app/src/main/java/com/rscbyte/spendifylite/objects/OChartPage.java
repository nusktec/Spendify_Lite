package com.rscbyte.spendifylite.objects;

import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;

import com.rscbyte.spendifylite.R;

public class OChartPage extends BaseObservable {

    private String txtTypical = "0.00";
    private String txtTypical2 = "0.00";
    private String txtBelowTypical = "0.00";
    private String txtSpentSoFar = "0.00";
    private String txtStatement = "Expense Indicator";
    private String txtVariesTyical = "Balance";
    private String txtIncomeThisM = "Percentage Income Spent";
    private int txtColor = R.color.green_500;
    private int txtColor2 = R.color.grey_500;

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
        //view.setCardBackgroundColor(view.getContext().getResources().getColor(txtColor));
    }

    @BindingAdapter({"app:colorbg"})
    public static void setTxtColorBg(CardView view, int txtColor) {
        view.setCardBackgroundColor(view.getContext().getResources().getColor(txtColor));
    }

    public void setTxtColor(int txtColor) {
        this.txtColor = txtColor;
    }

    public String getTxtIncomeThisM() {
        return txtIncomeThisM;
    }

    public void setTxtIncomeThisM(String txtIncomeThisM) {
        this.txtIncomeThisM = txtIncomeThisM;
    }

    public String getTxtTypical2() {
        return txtTypical2;
    }

    public void setTxtTypical2(String txtTypical2) {
        this.txtTypical2 = txtTypical2;
    }

    public int getTxtColor2() {
        return txtColor2;
    }

    public void setTxtColor2(int txtColor2) {
        this.txtColor2 = txtColor2;
    }
}
