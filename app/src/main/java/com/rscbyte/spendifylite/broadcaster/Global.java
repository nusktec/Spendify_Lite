package com.rscbyte.spendifylite.broadcaster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Global extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //anything can happen
        Toast.makeText(context, "Hello, charger plugged", Toast.LENGTH_LONG).show();
    }
}
