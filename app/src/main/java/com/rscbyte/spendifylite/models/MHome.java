package com.rscbyte.spendifylite.models;

import com.rscbyte.spendifylite.R;

import java.util.ArrayList;

public class MHome {
    private String variesTypical = "";
    private String txtStatement = "";
    private int txtColor = R.color.red_500;
    //start another sections
    private String txtSpentSoFar = "";
    private String txtTypical2 = "";
    private String txtTypical = "";
    private String txtBelowTypical = "";
    private String txtIncomeThisM = "";
    private String txtIncomeThis = "";
    private float _spent_sofar = 0;
    private int _typical_solve = 0;
    private int tooltipColor = R.color.red_500;
    private int data_counter = 0;
    //set for chart entries
    ArrayList pieEntries = new ArrayList();

    public MHome() {
    }

    public String getVariesTypical() {
        return variesTypical;
    }

    public void setVariesTypical(String variesTypical) {
        this.variesTypical = variesTypical;
    }

    public String getTxtStatement() {
        return txtStatement;
    }

    public void setTxtStatement(String txtStatement) {
        this.txtStatement = txtStatement;
    }

    public int getTxtColor() {
        return txtColor;
    }

    public void setTxtColor(int txtColor) {
        this.txtColor = txtColor;
    }

    public String getTxtSpentSoFar() {
        return txtSpentSoFar;
    }

    public void setTxtSpentSoFar(String txtSpentSoFar) {
        this.txtSpentSoFar = txtSpentSoFar;
    }

    public String getTxtTypical2() {
        return txtTypical2;
    }

    public void setTxtTypical2(String txtTypical2) {
        this.txtTypical2 = txtTypical2;
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

    public String getTxtIncomeThisM() {
        return txtIncomeThisM;
    }

    public void setTxtIncomeThisM(String txtIncomeThisM) {
        this.txtIncomeThisM = txtIncomeThisM;
    }

    public String getTxtIncomeThis() {
        return txtIncomeThis;
    }

    public void setTxtIncomeThis(String txtIncomeThis) {
        this.txtIncomeThis = txtIncomeThis;
    }

    public float get_spent_sofar() {
        return _spent_sofar;
    }

    public void set_spent_sofar(float _spent_sofar) {
        this._spent_sofar = _spent_sofar;
    }

    public int get_typical_solve() {
        return _typical_solve;
    }

    public void set_typical_solve(int _typical_solve) {
        this._typical_solve = _typical_solve;
    }

    public ArrayList getPieEntries() {
        return pieEntries;
    }

    public void setPieEntries(Object pieEntries) {
        this.pieEntries.add(pieEntries);
    }

    public int getTooltipColor() {
        return tooltipColor;
    }

    public void setTooltipColor(int tooltipColor) {
        this.tooltipColor = tooltipColor;
    }

    public int getData_counter() {
        return data_counter;
    }

    public void setData_counter(int data_counter) {
        this.data_counter = data_counter;
    }
}
