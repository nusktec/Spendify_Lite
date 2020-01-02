package com.rscbyte.spendifylite.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rscbyte.spendifylite.R;
import com.rscbyte.spendifylite.Utils.Tools;

public class ScreenLock extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_lock);
        //set header white back
        Tools.setHeaderColor(this);
    }

    //init components
    public void init() {

    }
}
