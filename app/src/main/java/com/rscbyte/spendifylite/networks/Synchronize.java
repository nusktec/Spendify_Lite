package com.rscbyte.spendifylite.networks;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.rscbyte.spendifylite.Utils.Constants;
import com.rscbyte.spendifylite.Utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import static android.content.ContentValues.TAG;

public class Synchronize {

    //backup db
    public static void doBackupDb(final Activity ctx, String email) {
        try {
            ApplicationInfo ai = ctx.getPackageManager().getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            String mydb = bundle.getString("DATABASE");
            //form the local url
            File dbFile = ctx.getDatabasePath(mydb);
            //start uploads
            AndroidNetworking.upload(Constants.API_DOMAIN_URL + "/backup")
                    .addMultipartFile("backupdb", dbFile)
                    .addMultipartParameter("user", email)
                    .setTag("uploadDb")
                    .setPriority(Priority.HIGH)
                    .build()
                    .setUploadProgressListener(new UploadProgressListener() {
                        @Override
                        public void onProgress(long bytesUploaded, long totalBytes) {
                            // do anything with progress
                            //Tools.showToast(ctx, "Uploading...");
                        }
                    })
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // below code will be executed in the executor provided
                            // do anything with response
                            Log.e("dshbfsdkhbgsbdgjsdgj", response.toString());
                            try {
                                if (response.getBoolean("status")) {
                                    //Tools.showToast(ctx, "Backup updated");
                                } else {
                                    //Tools.showToast(ctx, "Unable to backup at the moment");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                //Tools.showToast(ctx, "Backup system refactored, try again");
                            }
                        }

                        @Override
                        public void onError(ANError error) {
                            // handle error
                            error.printStackTrace();
                        }
                    });
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Failed to load meta-data, NameNotFound: " + e.getMessage());
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to load meta-data, NullPointer: " + e.getMessage());
        }
    }
}
