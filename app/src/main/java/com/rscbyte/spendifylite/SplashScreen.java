package com.rscbyte.spendifylite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.rscbyte.spendifylite.Utils.Tools;
import com.rscbyte.spendifylite.activities.Dashboard;

public class SplashScreen extends AppCompatActivity {
    boolean firstCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //set toolbar for mobile
        Tools.setSystemBarColor(this, R.color.app_color_1);
        //check for permission
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkPermission(Manifest.permission.READ_SMS);
            }
        }, 3000);
    }

    //launcher
    public void startMain() {
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
