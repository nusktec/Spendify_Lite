package com.rscbyte.spendifylite;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.rscbyte.spendifylite.Utils.Tools;
import com.rscbyte.spendifylite.databinding.ActivityPermissionBinding;

import java.util.Objects;

public class Permissions extends AppCompatActivity {

    //Bind and use global data
    ActivityPermissionBinding bdx = null;
    Activity ctx = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //assign binds layout
        this.ctx = this;
        this.bdx = DataBindingUtil.setContentView(ctx, R.layout.activity_permission);
        //apply associated layout
        Tools.setHeaderColor(this);
        initControls();
    }

    // initialize controls
    public void initControls() {
        bdx.btnStartContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askForPermissions();
            }
        });
    }

    //check for permissions
    public void askForPermissions() {
        String[] permission = Objects.requireNonNull(getIntent().getStringExtra("permission")).split("~");
        ActivityCompat.requestPermissions(this, new String[]{permission[0], permission[1]}, 200);
    }

    //Permission listener
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 200:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                    finish();
                } else {
                    //not granted
                    Toast.makeText(ctx, "Denied !", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
