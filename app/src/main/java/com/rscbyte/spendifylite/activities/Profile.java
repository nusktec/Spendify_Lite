package com.rscbyte.spendifylite.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.orm.SugarContext;
import com.orm.SugarRecord;
import com.rscbyte.spendifylite.R;
import com.rscbyte.spendifylite.Utils.Tools;
import com.rscbyte.spendifylite.databinding.ActivityProfileBinding;
import com.rscbyte.spendifylite.models.MProfile;

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
        //data loader
        dataLoader();
    }

    //data loader
    public void dataLoader() {
        if (MProfile.count(MProfile.class) < 1) {
            Tools.showToast(ctx, "You have not setup user account");
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
            if (mProfile.getNotifications() == 1) {
                //male gender
                bdx.radioNotiOn.setChecked(true);
            } else {
                bdx.radioNotiOff.setChecked(true);
            }
            //sms
            if (mProfile.getSms() == 1) {
                //male gender
                bdx.radioSmsOn.setChecked(true);
            } else {
                bdx.radioSmsOff.setChecked(true);
            }
        }
    }

    //set header and toolbar
    @SuppressLint("SetTextI18n")
    public void initToolBar() {
        Tools.setSystemBarColor(ctx, R.color.app_color_1);
        bdx.toolbarTitle.setText("Account Settings");
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
        bdx.radioGroupNoti.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == bdx.radioNotiOn.getId()) {
                    notifications = 1;
                }
                if (i == bdx.radioNotiOff.getId()) {
                    notifications = 0;
                }
            }

        });

        //sms
        bdx.radioGroupSms.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == bdx.radioSmsOn.getId()) {
                    sms = 1;
                }
                if (i == bdx.radioSmsOff.getId()) {
                    sms = 0;
                }
            }

        });
    }

    //submit data
    public String gender = "M";
    public int notifications = 1;
    public int sms = 1;

    public void saveSettings() {
        //iterate for all view data
        if (bdx.txtName.getText().length() < 1 || bdx.txtEmail.getText().length() < 1 || bdx.txtPhone.getText().length() < 1 || bdx.txtQuote.getText().length() < 1) {
            //Empty fields
            Tools.showToast(ctx, "Settings not saved");
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
        //begin processing
        if (MProfile.count(MProfile.class) > 1) {
            //update
            SugarRecord.update(profile);
            dataLoader();
        } else {
            //insert new
            SugarRecord.save(profile);
            //reload data
            dataLoader();
        }
        Tools.showToast(ctx, "Settings saved !");
    }
}
