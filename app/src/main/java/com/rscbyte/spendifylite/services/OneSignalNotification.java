package com.rscbyte.spendifylite.services;

import android.content.Intent;

import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationReceivedResult;
import com.rscbyte.spendifylite.Utils.Tools;
import com.rscbyte.spendifylite.activities.Dashboard;

public class OneSignalNotification extends NotificationExtenderService {
    @Override
    protected boolean onNotificationProcessing(OSNotificationReceivedResult notification) {
        //override to my own notifications
        try {
            if (notification.payload.additionalData.getBoolean("report")) {
                //open report services
                startService(new Intent(this, ReportServices.class));
            } else {
                Tools.Notification(getBaseContext(), notification.payload.title, "Notification", notification.payload.body, 3, Dashboard.class, "No Data");
            }
        } catch (Exception ex) {
            //go silence from here
            Tools.Notification(getBaseContext(), notification.payload.title, "Notification", notification.payload.body, 3, Dashboard.class, "No Data");
        }
        return true;
    }
}
