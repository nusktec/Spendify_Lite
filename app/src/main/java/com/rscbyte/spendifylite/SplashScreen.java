package com.rscbyte.spendifylite;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.onesignal.OneSignal;
import com.orm.SugarContext;
import com.rscbyte.spendifylite.Interfaces.CallBacks;
import com.rscbyte.spendifylite.Interfaces.CallBacksArgs;
import com.rscbyte.spendifylite.Utils.Constants;
import com.rscbyte.spendifylite.Utils.Tools;
import com.rscbyte.spendifylite.activities.Dashboard;
import com.rscbyte.spendifylite.activities.Profile;
import com.rscbyte.spendifylite.activities.ScreenLock;
import com.rscbyte.spendifylite.models.MProfile;
import com.rscbyte.spendifylite.services.ReportServices;
import com.rscbyte.spendifylite.services.SMSFunctions;
import com.rscbyte.spendifylite.services.SMSService;

public class SplashScreen extends AppCompatActivity {
    boolean firstCheck = false;
    public static int DELAY_TIME_SEC = 1200;
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

    //run sms sync.
    private void runSMSSync() {
        findViewById(R.id.info_container).setVisibility(View.VISIBLE);
        new RunSMSAsync(this, new CallBacksArgs() {
            @Override
            public void onOkay(int i) {
                if (i == 1) {
                    ((TextView) findViewById(R.id.txt_info)).setText("Running quick analysis...");
                } else if (i == 2) {
                    ((TextView) findViewById(R.id.txt_info)).setText("Loading transactions chart..");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //open dash board
                            if (profile.getProtects() == 1) {
                                startActivity(new Intent(SplashScreen.this, ScreenLock.class));
                                finish();
                            } else {
                                startActivity(new Intent(SplashScreen.this, Dashboard.class));
                                finish();
                            }
                        }
                    }, 2000);
                } else {
                    ((TextView) findViewById(R.id.txt_info)).setText("Sorry, an invalid transaction data detected !");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((TextView) findViewById(R.id.txt_info)).setText("Could not start spendify properly");
                        }
                    }, 200);
                }
            }
        }).execute();
    }

    //launcher
    public void startMain() {
        //reinitialize
        profile = MProfile.findById(MProfile.class, 1);
        //sign-up profile
        if (MProfile.count(MProfile.class) > 0) {
            //start service before every other
            //startService(new Intent(this, SMSService.class));
            //Think to start the new class
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    runSMSSync();
                }
            }, DELAY_TIME_SEC);
        } else {
            //profile panel
            startActivity(new Intent(SplashScreen.this, Profile.class).putExtra("fs", 1));
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

    //sms puller ans sync
    private class RunSMSAsync extends AsyncTask<Void, Integer, Integer> {
        CallBacksArgs callBacks;
        public Activity ctx;

        RunSMSAsync(Activity ctxs, CallBacksArgs callBacks) {
            this.callBacks = callBacks;
            this.ctx = ctxs;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            try {
                //run SMS sync.
                new SMSFunctions(ctx, new CallBacksArgs() {
                    @Override
                    public void onOkay(int i) {
                        publishProgress(i);
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
                publishProgress(0);
            }
            return 2;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            callBacks.onOkay(values[0]);
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            callBacks.onOkay(1);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            //keep it to your self;
            super.onPostExecute(integer);
        }
    }
}
