package com.rscbyte.spendifylite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.orm.SugarContext;
import com.rscbyte.spendifylite.Utils.Constants;
import com.rscbyte.spendifylite.Utils.Tools;
import com.rscbyte.spendifylite.activities.Dashboard;
import com.rscbyte.spendifylite.models.MProfile;
import com.rscbyte.spendifylite.services.SMSService;

public class SplashScreen extends AppCompatActivity {
    boolean firstCheck = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //initialize suger orm
        SugarContext.init(this);
        //set toolbar for mobile
        Tools.setSystemBarColor(this, R.color.light_white);
        Tools.setSystemBarLight(this);
        //check for permission
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkPermission(Manifest.permission.READ_SMS);
            }
        }, 3000);
        //load license copy text
        if (MProfile.count(MProfile.class) > 0) {
            String name = (MProfile.findById(MProfile.class, 1)).getNames();
            ((TextView) findViewById(R.id.txt_license)).setText("licensed to " + name.toLowerCase());
        }
        //clear preference
        getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE).edit()
                .putInt(Constants.SHARED_ALERT_KEY, 0).apply();
    }

    //launcher
    public void startMain() {
        //start service before every other
        startService(new Intent(this, SMSService.class));
        //Think to start the new class
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, Dashboard.class));
                finish();
            }
        }, 3000);
    }

    //Simple permission to check
    public void checkPermission(String permission) {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
            //Open permission screen
            firstCheck = true;
            startActivity(new Intent(SplashScreen.this, Permissions.class).putExtra("permission", Manifest.permission.READ_SMS));
        } else {
            startMain();
        }
    }

    //onResume activities
    @Override
    protected void onResume() {
        super.onResume();
        if (firstCheck)
            checkPermission(Manifest.permission.READ_SMS);
    }
}
