package com.rscbyte.spendifylite.models;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.rscbyte.spendifylite.objects.OSms;

import java.util.ArrayList;
import java.util.List;

public class MSms {

    private Context ctx = null;

    MSms() {
    }

    public MSms(Context ctx) {
        this.ctx = ctx;
    }

    public List<OSms> getAllSms() {
        List<OSms> lstSms = new ArrayList<OSms>();
        Uri message = Uri.parse("content://sms/");
        ContentResolver cr = ctx.getContentResolver();

        Cursor c = cr.query(message, null, null, null, null);
        //activity.startManagingCursor(c);
        assert c != null;
        if (c.moveToFirst()) {
            for (c.moveToLast(); !c.isBeforeFirst(); c.moveToPrevious()) {
                OSms objSms = new OSms();
                if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                    objSms.setId(c.getString(c.getColumnIndexOrThrow("_id")));
                    objSms.setAddress(c.getString(c
                            .getColumnIndexOrThrow("address")));
                    objSms.setMsg(c.getString(c.getColumnIndexOrThrow("body")));
                    objSms.setReadState(c.getString(c.getColumnIndex("read")));
                    objSms.setTime(c.getString(c.getColumnIndexOrThrow("date")));
                    objSms.setFolderName("inbox");
                    objSms.setSmsType(1);
                }
                lstSms.add(objSms);
            }
        }
        // else {
        // throw new RuntimeException("You have no SMS");
        // }
        c.close();

        return lstSms;
    }

}
