package com.rscbyte.spendifylite.models;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.rscbyte.spendifylite.objects.OSms;

import java.util.ArrayList;
import java.util.List;

public class MSms {

    public List<OSms> getAllSms(Activity activity) {
        List<OSms> lstSms = new ArrayList<OSms>();
        OSms objSms = new OSms();
        Uri message = Uri.parse("content://sms/");
        ContentResolver cr = activity.getContentResolver();

        Cursor c = cr.query(message, null, null, null, null);
        activity.startManagingCursor(c);
        assert c != null;
        int totalSMS = c.getCount();

        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {

                objSms = new OSms();
                objSms.setId(c.getString(c.getColumnIndexOrThrow("_id")));
                objSms.setAddress(c.getString(c
                        .getColumnIndexOrThrow("address")));
                objSms.setMsg(c.getString(c.getColumnIndexOrThrow("body")));
                objSms.setReadState(c.getString(c.getColumnIndex("read")));
                objSms.setTime(c.getString(c.getColumnIndexOrThrow("date")));
                if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                    objSms.setFolderName("inbox");
                    objSms.setSmsType(1);
                } else {
                    objSms.setSmsType(2);
                    objSms.setFolderName("sent");
                }

                lstSms.add(objSms);
                c.moveToNext();
            }
        }
        // else {
        // throw new RuntimeException("You have no SMS");
        // }
        c.close();

        return lstSms;
    }

}
