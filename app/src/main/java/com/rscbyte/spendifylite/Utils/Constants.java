package com.rscbyte.spendifylite.Utils;

import com.rscbyte.spendifylite.models.MProfile;

public class Constants {
    //global variables
    public static String API_DOMAIN_URL = "http://spendify.ng/index.php/api";
    public static String API_IMG_DOMAIN_URL = "http://spendify.ng/uploads";
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
