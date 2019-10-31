package com.rscbyte.spendifylite.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.rscbyte.spendifylite.R;
import com.rscbyte.spendifylite.Utils.Tools;
import com.rscbyte.spendifylite.databinding.ActivityProfileBinding;

public class Profile extends AppCompatActivity {

    //Main layout inflater holder
    ActivityProfileBinding bdx = null;
    Activity ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize context
        this.ctx = this;
        this.bdx = DataBindingUtil.setContentView(ctx, R.layout.activity_profile);
        //initialize toolbar
        initToolBar();
        //initialize components
        componentsInit();
    }

    //set header and toolbar
    @SuppressLint("SetTextI18n")
    public void initToolBar() {
        Tools.setSystemBarColor(ctx, R.color.app_color_1);
        bdx.toolbarTitle.setText("Account Settings");
    }

    //components initializer
    public void componentsInit() {
        //left btn initializer
        bdx.toolbarLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
