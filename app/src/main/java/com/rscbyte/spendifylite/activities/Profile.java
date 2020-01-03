package com.rscbyte.spendifylite.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.orm.SugarRecord;
import com.rscbyte.spendifylite.R;
import com.rscbyte.spendifylite.Utils.Tools;
import com.rscbyte.spendifylite.adapters.CountryList;
import com.rscbyte.spendifylite.databinding.ActivityProfileBinding;
import com.rscbyte.spendifylite.models.MProfile;

public class Profile extends AppCompatActivity {

    //Main layout inflater holder
    ActivityProfileBinding bdx = null;
    Activity ctx;
    //submit data
    public String gender = "M";
    public int notifications = 1;
    public int sms = 1;
    public int protects = 1;
    public int currency = 0;
    public String country = "Nigeria (NGN)";
    public String symbol = "NGN";

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
        //data loader
        dataLoader();
    }

    //data loader
    public void dataLoader() {
        //setup countries
        final CountryList countryList = new CountryList(ctx);
        bdx.spinnerCountry.setAdapter(countryList);
        bdx.setSmsState(true);
        if (MProfile.count(MProfile.class) < 1) {
            Tools.showToast(ctx, "You have not setup user profile");
            return;
        }
        MProfile mProfile = MProfile.findById(MProfile.class, 1);
        if (mProfile.getId() > 0) {
            bdx.txtName.setText(Tools.capsWords(mProfile.getNames()));
            bdx.txtEmail.setText(mProfile.getEmail());
            bdx.txtPhone.setText(mProfile.getPhone());
            bdx.txtQuote.setText(mProfile.getQuotes());
            //fix gender
            if (mProfile.getGender().equals("M")) {
                //male gender
                bdx.radioMale.setChecked(true);
            } else {
                bdx.radioFemale.setChecked(true);
            }
            //notifications
            bdx.switchNoti.setChecked(mProfile.getNotifications() == 1);
            //sms sync
            bdx.switchSms.setChecked(mProfile.getSms() == 1);
            //male gender
            bdx.switchProtect.setChecked(mProfile.getProtects() == 1);
            //country selection
            bdx.spinnerCountry.setSelection(mProfile.getCurrency());
            bdx.setSmsState(bdx.switchSms.isChecked());
        }
    }

    //set header and toolbar
    @SuppressLint("SetTextI18n")
    public void initToolBar() {
        Tools.setHeaderColor(this);
        bdx.toolbarTitle.setText("Profile Settings");
        //left btn initializer
        bdx.toolbarLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //components initializer
    public void componentsInit() {
        //save button
        bdx.btnSaveSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSettings();
            }
        });
        //gender selections
        bdx.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == bdx.radioMale.getId()) {
                    gender = "M";
                }
                if (i == bdx.radioFemale.getId()) {
                    gender = "F";
                }
            }
        });
        //notifications
        bdx.switchNoti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    notifications = 1;
                } else {
                    notifications = 0;
                }
            }
        });

        //sms
        bdx.switchSms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    sms = 1;
                } else {
                    sms = 0;
                }
                bdx.setSmsState(b);
            }
        });
        //sms
        bdx.switchProtect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    protects = 1;
                } else {
                    protects = 0;
                }
            }
        });
    }

    public void saveSettings() {
        //iterate for all view data
        if (bdx.txtName.getText().length() < 1 || bdx.txtEmail.getText().length() < 1 || bdx.txtPhone.getText().length() < 1 || bdx.txtQuote.getText().length() < 1) {
            //Empty fields
            Tools.showToast(ctx, "Complete user profile");
            return;
        }
        //format data
        MProfile profile = new MProfile();
        profile.setNames(bdx.txtName.getText().toString());
        profile.setEmail(bdx.txtEmail.getText().toString());
        profile.setPhone(bdx.txtPhone.getText().toString());
        profile.setGender(gender);
        profile.setQuotes(bdx.txtQuote.getText().toString());
        profile.setNotifications(notifications);
        profile.setSms(sms);
        profile.setProtects(protects);
        profile.setCurrency(currency);
        profile.setCountry(country);
        profile.setSymbol(symbol);
        //begin processing
        if (MProfile.count(MProfile.class) > 1) {
            //update
            SugarRecord.update(profile);
        } else {
            //insert new
            SugarRecord.save(profile);
            //reload data
        }
        Tools.showToast(ctx, "Profile saved !");
        finish();
    }
}
