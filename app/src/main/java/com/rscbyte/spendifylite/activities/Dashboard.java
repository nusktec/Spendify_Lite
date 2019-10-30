package com.rscbyte.spendifylite.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.os.Bundle;

import com.rscbyte.spendifylite.R;
import com.rscbyte.spendifylite.Utils.Tools;
import com.rscbyte.spendifylite.databinding.ActivityDashboardBinding;

public class Dashboard extends AppCompatActivity {

    //Main layout inflater holder
    ActivityDashboardBinding bdx = null;
    Activity ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //assign context menu
        this.ctx = this;
        //inflate layout
        bdx = DataBindingUtil.setContentView(ctx, R.layout.activity_dashboard);
        //initialize toolbar
        initToolBar();
    }

    //set header and toolbar
    public void initToolBar() {
        Tools.setSystemBarColor(ctx, R.color.app_color_1);
    }
}
