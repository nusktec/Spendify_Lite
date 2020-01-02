package com.rscbyte.spendifylite.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.rscbyte.spendifylite.R;
import com.rscbyte.spendifylite.Utils.Tools;
import com.rscbyte.spendifylite.databinding.ActivityScreenLockBinding;

public class ScreenLock extends AppCompatActivity {

    private ActivityScreenLockBinding bdx;
    private Activity ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ctx = this;
        this.bdx = DataBindingUtil.setContentView(ctx, R.layout.activity_screen_lock);
        //set header white back
        Tools.setHeaderColor(this);
        init();
    }

    //init components
    public void init() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
