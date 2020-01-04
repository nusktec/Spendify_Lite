package com.rscbyte.spendifylite.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.rscbyte.spendifylite.R;
import com.rscbyte.spendifylite.Utils.Tools;
import com.rscbyte.spendifylite.databinding.ActivityScreenLockBinding;

import me.aflak.libraries.callback.FingerprintDialogCallback;
import me.aflak.libraries.dialog.FingerprintDialog;

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

    //result code
    int INTENT_AUTHENTICATE = 200;

    //init components
    public void init() {
        //use finger instead btn
        bdx.btnUseFinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pop for finger print
                displayBiometricPrompt();
            }
        });

        bdx.btnUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

                assert km != null;
                if (km.isKeyguardSecure()) {
                    Intent authIntent = km.createConfirmDeviceCredentialIntent("Spendify Protects", "Enter your device PIN to unlock");
                    startActivityForResult(authIntent, INTENT_AUTHENTICATE);
                } else {
                    //no password
                    finish();
                }
            }
        });
    }

    private void displayBiometricPrompt() {
        if (FingerprintDialog.isAvailable(this)) {
            FingerprintDialog.initialize(this)
                    .title("Spendify Protects")
                    .message("Unlock with your finder to begin transaction")
                    .callback(new FingerprintDialogCallback() {
                        @Override
                        public void onAuthenticationSucceeded() {
                            finish();
                        }

                        @Override
                        public void onAuthenticationCancel() {

                        }
                    }).show();
        } else {
            Tools.showToast(this, "Device not supported, use code instead");
        }
    }

    @Override
    public void onBackPressed() {
        Tools.showToast(this, "Can only exit !");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_AUTHENTICATE) {
            if (resultCode == RESULT_OK) {
                //do something you want when pass the security
                finish();
            }
        }
    }
}
