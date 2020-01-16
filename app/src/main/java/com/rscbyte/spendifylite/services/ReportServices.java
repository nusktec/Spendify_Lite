package com.rscbyte.spendifylite.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.orm.query.Condition;
import com.orm.query.Select;
import com.rscbyte.spendifylite.Utils.Tools;
import com.rscbyte.spendifylite.activities.Dashboard;
import com.rscbyte.spendifylite.models.MData;
import com.rscbyte.spendifylite.models.MProfile;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ReportServices extends Service {
    private Context ctx = null;
    //Good morning Rev....Your total expense is N71,000 as of yesterday! Spend Wisely!

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.ctx = this;
        //start a reminder
        try {
            circleClock();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return START_STICKY;
    }

    //start circle clock
    private void circleClock() {
        //fetch from db and sums up !
        try {
            alertPreviousDayExpense();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //86400000
    }

    private void alertPreviousDayExpense() {
        if (MProfile.count(MProfile.class) == 0) {
            return;
        }
        MProfile mp = MProfile.findById(MProfile.class, 1);
        int _lastExpense = 0;
        //get this month account total expense
        Select select2 = Select.from(MData.class).where(Condition.prop("TRX_MONTH").eq(Tools.getMonth() + "")).and(Condition.prop("TRX_YEAR").eq(Tools.getYear() + "")).and(Condition.prop("TRX_DAY").eq((Tools.getDay() - 1) + ""));
        List<MData> mData2 = select2.list();
        for (MData t2 : mData2) {
            //work for the current month
            if (t2.getTrxType() == 2) _lastExpense += Float.parseFloat(t2.getTrxValue());
        }
        //prepare statement
        String _statemen;
        if (_lastExpense > 0) {
            _statemen = "Dear " + Tools.capsfLetter(mp.getNames().split(" ")[0]) + "....Your total expense is " + mp.getSymbol() + Tools.doCuurency(_lastExpense) + " as of yesterday ! Spend Wisely!";
        } else {
            _statemen = "Dear " + Tools.capsfLetter(mp.getNames().split(" ")[0]) + ".... You have no expense as of yesterday";
        }
        Tools.Notification(getBaseContext(), "Expense Manager", "Expense Reporter", _statemen, 4, Dashboard.class, "No Data");

    }
}
