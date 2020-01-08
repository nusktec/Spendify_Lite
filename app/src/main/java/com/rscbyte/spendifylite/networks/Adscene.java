package com.rscbyte.spendifylite.networks;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.rscbyte.spendifylite.R;
import com.rscbyte.spendifylite.databinding.DialogAdvertBinding;

import java.net.URL;

public class Adscene {
    private int _counter = 10;
    private Activity ctx = null;
    private Handler handler = null;

    public Activity getCtx() {
        return ctx;
    }

    public void setCtx(Activity ctx) {
        this.ctx = ctx;
    }

    public void showAdvert(final Activity ctx) {
        final Dialog dialog = new Dialog(new ContextThemeWrapper(ctx, R.style.DialogSlideAnim));
        final DialogAdvertBinding infoBinding = DataBindingUtil.inflate(LayoutInflater.from(ctx), R.layout.dialog_advert, null, false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(infoBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);

        //download image
        Glide.with(ctx)
                .load("http://jollyfinder.biz/wp-content/uploads/2019/02/CREDITVILLE-4.jpg")
                .into(infoBinding.imgUrl);

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
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();
        dialog.getWindow().setAttributes(lp);
        //start timer for closing secs
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                infoBinding.btnClose.setText(String.format("%02d", _counter));
                if (_counter < 1) {
                    infoBinding.btnClose.setText("X");
                    infoBinding.btnClose.setEnabled(true);
                } else {
                    handler.postDelayed(this, 1000);
                }
                _counter--;
            }
        }, 1000);
    }
}
