package com.rscbyte.spendifylite.Utils;

import com.rscbyte.spendifylite.models.MProfile;

public class Constants {
    //global variables
    public static String API_DOMAIN_URL = "http://192.168.8.100/spendify/api";
    public static String API_IMG_DOMAIN_URL = "http://192.168.8.100/spendify/uploads";
    public static String SHARED_PREF_NAME = "rscshared_spendify";
    public static String SHARED_ALERT_KEY = "newAlert";
    public static String SHARED_NEW_OPEN_KEY = "newLaunched";

    public static String getCurrency() {
        String symb = "NGN";
        if (MProfile.count(MProfile.class) > 0) {
            symb = MProfile.findById(MProfile.class, 1).getSymbol();
        }
        return symb;
    }
}
