package com.rscbyte.spendifylite.networks;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jacksonandroidnetworking.JacksonParserFactory;
import com.rscbyte.spendifylite.R;
import com.rscbyte.spendifylite.Utils.Constants;
import com.rscbyte.spendifylite.databinding.DialogAdvertBinding;
import com.rscbyte.spendifylite.models.MAdscene;

import okhttp3.OkHttpClient;

public class Adscene {
    private int _counter = 10;
    private Activity ctx = null;
    private Handler handler = null;

    public Activity getCtx() {
        return ctx;
    }

    public void setCtx(Activity ctx) {
        this.ctx = ctx;
        // Adding an Network Interceptor for Debugging purpose :
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addNetworkInterceptor(new HttpLoggingInterceptor())
                .build();
        AndroidNetworking.initialize(ctx, okHttpClient);
        AndroidNetworking.setParserFactory(new JacksonParserFactory());
    }

    public void showAdvert(final Activity ctx, String email) {
        final Dialog dialog = new Dialog(new ContextThemeWrapper(ctx, R.style.DialogSlideAnim));
        final DialogAdvertBinding infoBinding = DataBindingUtil.inflate(LayoutInflater.from(ctx), R.layout.dialog_advert, null, false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(infoBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //display info
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().getAttributes().windowAnimations = R.style.WindowAnimationTransition;

        AndroidNetworking.get(Constants.API_DOMAIN_URL + "/adverts")
                .addQueryParameter("email", email)
                .setTag(ctx)
                .setPriority(Priority.HIGH)
                .build()
                .getAsObject(MAdscene.class, new ParsedRequestListener<MAdscene>() {
                    @Override
                    public void onResponse(final MAdscene response) {
                        Glide.with(ctx)
                                .load(Constants.API_IMG_DOMAIN_URL + "/adverts/" + response.getAid() + ".jpg")
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        showAdvert(response.getAurl(), infoBinding, dialog);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        showAdvert(response.getAurl(), infoBinding, dialog);
                                        return false;
                                    }
                                })
                                .placeholder(R.drawable.ads_baner)
                                .into(infoBinding.imgUrl);
                        dialog.show();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Error From Adverts", "See Below");
                        anError.printStackTrace();
                    }
                });
    }

    private void showAdvert(final String burl, final DialogAdvertBinding infoBinding, final Dialog dialog) {
        //btn close
        infoBinding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        //btn link
        infoBinding.btnLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(burl));
                    dialog.getContext().startActivity(browserIntent);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        //start timer for closing secs
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                infoBinding.btnClose.setText(String.format("%02d", _counter));
                if (_counter < 1) {
                    infoBinding.btnClose.setText("X");
                    infoBinding.btnClose.setEnabled(true);
                    infoBinding.btnLink.setVisibility(View.VISIBLE);
                } else {
                    handler.postDelayed(this, 1000);
                }
                _counter--;
            }
        }, 1000);
    }
}
