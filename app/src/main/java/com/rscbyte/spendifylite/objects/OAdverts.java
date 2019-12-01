package com.rscbyte.spendifylite.objects;

import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.library.baseAdapters.BR;

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
        notifyPropertyChanged(BR.imgUrl);
    }

    @Bindable
    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
        notifyPropertyChanged(BR.textBody);
    }

    @Bindable
    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
        notifyPropertyChanged(BR.linkUrl);
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
