package com.rscbyte.spendifylite.networks;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.databinding.DataBindingUtil;

import com.rscbyte.spendifylite.R;
import com.rscbyte.spendifylite.databinding.DialogAdvertBinding;

public class Adscene {

    public static void showAdvert(Activity ctx) {
        final Dialog dialog = new Dialog(ctx);
        DialogAdvertBinding infoBinding = DataBindingUtil.inflate(LayoutInflater.from(ctx), R.layout.dialog_advert, null, false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(infoBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        infoBinding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        //display info
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}
