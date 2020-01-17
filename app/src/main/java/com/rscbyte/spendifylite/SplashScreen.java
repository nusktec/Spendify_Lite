package com.rscbyte.spendifylite;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.onesignal.OneSignal;
import com.orm.SugarContext;
import com.rscbyte.spendifylite.Utils.Constants;
import com.rscbyte.spendifylite.Utils.Tools;
import com.rscbyte.spendifylite.activities.Dashboard;
import com.rscbyte.spendifylite.activities.Profile;
import com.rscbyte.spendifylite.activities.ScreenLock;
import com.rscbyte.spendifylite.models.MProfile;
import com.rscbyte.spendifylite.services.ReportServices;
import com.rscbyte.spendifylite.services.SMSService;

public class SplashScreen extends AppCompatActivity {
    boolean firstCheck = false;
    public static int DELAY_TIME_SEC = 1500;
    private MProfile profile;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //initialize sugar orm
        SugarContext.init(this);
        //set toolbar for mobile
        Tools.setSystemBarColor(this, R.color.app_color_2);
        //Tools.setSystemBarLight(this);
        //check for permission
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkPermission(Manifest.permission.READ_SMS, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }, DELAY_TIME_SEC);
        //load license copy text
        if (MProfile.count(MProfile.class) > 0) {
            profile = MProfile.findById(MProfile.class, 1);
            String name = profile.getNames();
            ((TextView) findViewById(R.id.txt_license)).setText("licensed to " + name.toLowerCase());
        }
        //clear preference
        getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE).edit()
                .putInt(Constants.SHARED_ALERT_KEY, 0).apply();

        getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE).edit()
                .putInt(Constants.SHARED_NEW_OPEN_KEY, 0).apply();
        //start reporter
        //startService(new Intent(this, ReportServices.class));
    }

    //launcher
    public void startMain() {
        //reinitialize
        profile = MProfile.findById(MProfile.class, 1);
        //sign-up profile
        if (MProfile.count(MProfile.class) > 0) {
            //start service before every other
            startService(new Intent(this, SMSService.class));
            //Think to start the new class
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (profile.getProtects() == 1) {
                        startActivity(new Intent(SplashScreen.this, ScreenLock.class));
                        finish();
                    } else {
                        startActivity(new Intent(SplashScreen.this, Dashboard.class));
                        finish();
                    }
                }
            }, DELAY_TIME_SEC);
        } else {
            //profile panel
            //startActivity(new Intent(SplashScreen.this, Profile.class).putExtra("fs", 1));
        }
    }

    //Simple permission to check
    public void checkPermission(String... permission) {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(this, permission[0]) == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(this, permission[1]) == PackageManager.PERMISSION_DENIED) {
            //mark as new lunch
            getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE).edit().putInt(Constants.SHARED_NEW_OPEN_KEY, 1).apply();
            //Open permission screen
            firstCheck = true;
            startActivity(new Intent(SplashScreen.this, Permissions.class).putExtra("permission", Manifest.permission.READ_SMS + "~" + Manifest.permission.READ_EXTERNAL_STORAGE));
        } else {
            startMain();
        }
    }

    //onResume activities
    @Override
    protected void onResume() {
        if (firstCheck)
            //recreate();
            checkPermission(Manifest.permission.READ_SMS, Manifest.permission.READ_EXTERNAL_STORAGE);
        super.onResume();
    }
}
