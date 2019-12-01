package com.rscbyte.spendifylite.objects;

import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

public class OAdverts extends BaseObservable {
    private String imgUrl = "";
    private String textBody = "";
    private String linkUrl = "";

    public OAdverts() {
    }

    @Bindable
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Bindable
    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    @Bindable
    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    //Apply images
    @BindingAdapter({"app:loader"})
    public static void loadImage(ImageView img, String url) {

    }

    //Open browser
    @BindingAdapter({"app:link"})
    public static void loadImage(AppCompatButton btn, String url) {

    }
}
