package com.rscbyte.spendifylite.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.rscbyte.spendifylite.R;
import com.rscbyte.spendifylite.Utils.Constants;
import com.rscbyte.spendifylite.Utils.Tools;
import com.rscbyte.spendifylite.databinding.ActivityFragmentChartBinding;
import com.rscbyte.spendifylite.models.MData;
import com.rscbyte.spendifylite.models.MProfile;
import com.rscbyte.spendifylite.networks.Synchronize;
import com.rscbyte.spendifylite.objects.OChartPage;
import com.tooltip.Tooltip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FragmentChart extends Fragment {

    //make a public variables
    private ActivityFragmentChartBinding bdx = null;
    private Activity ctx = null;
    //Default settings
    private MProfile mProfile = new MProfile();
    private boolean isLogged = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //ass layout binding to it parents
        this.ctx = getActivity();
        bdx = DataBindingUtil.inflate(inflater, R.layout.activity_fragment_chart, container, false);
        bdx.setD(new OChartPage());
        //establish data
        main();
        return bdx.getRoot();
    }

    //main methods
    public void main() {
        //initialize toolbar
        //left btn initializer
        bdx.toolbarLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ctx, Profile.class), 230);
            }
        });
        //set today's time
        bdx.txtTimeNow.setText("Summary as of " + Tools.getFormattedDateSimple());
        try {
            //print chart
            printChart();
            //initialize
            initComponents();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    //charts properties
    private PieChart pieChart;
    private PieData pieData;
    private PieDataSet pieDataSet;
    private ArrayList pieEntries;

    //formulate data set
    private void printChart() {
        //redeclare profile
        mProfile = new MProfile();
        //initialize
        pieChart = bdx.pieChart;
        prepareChart();
        pieDataSet = new PieDataSet(pieEntries, "");
        pieData = new PieData(pieDataSet);
        pieChart.setCenterText("Monthly\nExpense");
        pieChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD);
        pieChart.setCenterTextColor(getResources().getColor(R.color.app_color_1Dp));
        pieChart.setVerticalFadingEdgeEnabled(true);
        pieChart.setData(pieData);
        pieChart.setEntryLabelColor(getResources().getColor(R.color.grey_800));
        pieChart.setEntryLabelTypeface(Typeface.DEFAULT_BOLD);
        pieChart.setDrawSlicesUnderHole(true);
        pieChart.animateX(500);
        pieChart.getDescription().setText("Spendify Data Chart");
        //apply colors
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setSliceSpace(2f);
        pieDataSet.setValueTextColor(R.color.app_color_1Dp);
        pieDataSet.setValueTextSize(10f);
        pieDataSet.setSliceSpace(5f);
        pieDataSet.setValueLinePart1Length(.5f);
        pieDataSet.setValueLinePart2Length(.2f);
        pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setValueLineWidth(3f);
        pieDataSet.setUsingSliceColorAsValueLineColor(true);
        pieDataSet.setValueTypeface(Typeface.DEFAULT_BOLD);
        pieChart.setHoleColor(Color.WHITE);
        bdx.pieChart.animate();
    }

    //prepare chart entries
    private Tooltip tooltip = null; //tooltip;

    void showToolTips(final String msg, final int color) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tooltip = new Tooltip.Builder(bdx.trxComparison)
                        .setText(msg)
                        .setCancelable(true)
                        .setDismissOnClick(true)
                        .setTextSize(16.0f)
                        .setCornerRadius(10.0f)
                        .setPadding(10)
                        .setTextColor(Color.WHITE)
                        .setTypeface(Typeface.DEFAULT_BOLD)
                        .setBackgroundColor(Objects.requireNonNull(getContext()).getResources().getColor(color))
                        .show();
            }
        }, 2000);
    }

    private void prepareChart() {
        //fetch strictly on moving date
        float _moving_average3 = 0;
        //monthly moving average
        int _monthly_average_target = 1;
        //assign dictionary value holder
        Map<String, Float> tmpValue = new HashMap<>();
        //get 3 months way back
        long passedMonth = Long.parseLong(Tools.getVariesTimeStamp(-4));
        //iterate last 3 month expense data
        float _spent_for_the_month = 0;
        String _month_name = "";
        int month_changes = 0;
        float _moving_average = 0;
        Select select = Select.from(MData.class).where(Condition.prop("TRX_STP").gt(passedMonth)).and(Condition.prop("TRX_MONTH").notEq(Tools.getMonth())).and(Condition.prop("TRX_TYPE").eq(2));
        List<MData> mData = select.list();
        for (MData t : mData) {
            //check date and break if passed today
            if (Integer.parseInt(t.getTrxDay()) <= Tools.getDay()) {
                //check for data different
                if (month_changes == 0) {
                    month_changes = Integer.parseInt(t.getTrxMonth());
                }
                //reset as data changed
                if (month_changes != Integer.parseInt(t.getTrxMonth())) {
                    _spent_for_the_month = 0;
                    month_changes = 0;
                    _monthly_average_target++;
                }
                //check for debit data only
                if (t.getTrxType() == 2) {
                    //add to map
                    _spent_for_the_month += Float.parseFloat(t.getTrxValue());
                    _month_name = Tools.getMonthAscAlpha(Integer.parseInt(t.getTrxMonth()));
                    tmpValue.put(_month_name, _spent_for_the_month);
                    //check for the last month
                    if (Integer.parseInt(t.getTrxMonth()) == Tools.timepstamp2Month(Long.parseLong(Tools.getVariesTimeStamp(-1)))) {
                        _moving_average += Float.parseFloat(t.getTrxValue());
                    }
                }
            }
        }
        //get how many months in total
        _monthly_average_target = tmpValue.size();
        float _spent_so_far = 0;
        float _income_this_month = 0;
        //get this month account total expense
        Select select2 = Select.from(MData.class).where(Condition.prop("TRX_MONTH").eq(Tools.getMonth() + "")).and(Condition.prop("TRX_YEAR").eq(Tools.getYear() + ""));
        List<MData> mData2 = select2.list();
        for (MData t2 : mData2) {
            //work for the current month
            //calculations for income
            if (t2.getTrxType() == 2) _spent_so_far += Float.parseFloat(t2.getTrxValue());
            else _income_this_month += Float.parseFloat(t2.getTrxValue());
        }

        //solve for typical
        float _typicalSolve = (_moving_average / 1);
        //solve for differences
        float _variesTypical = _spent_so_far - _typicalSolve;
        //determine over spent or less
        if (_spent_so_far > _typicalSolve) {
            //you above typical
            if (_typicalSolve > 0) {
                bdx.getD().setTxtVariesTyical("Above Usual");
                bdx.getD().setTxtColor(R.color.red_800);
                //remark statement
                bdx.getD().setTxtStatement("You are in the red zone.\nReview your past spending to stay on track");
                //show tooltip
                showToolTips("You are in the red zone.\nReview your past spending to stay on track", R.color.red_800);
            } else {
                bdx.getD().setTxtVariesTyical("Difference");
                bdx.getD().setTxtColor(R.color.orange_800);
                //remark statement
                bdx.getD().setTxtStatement("No previous transaction to determine usual expense");
                //show tooltip
                showToolTips("No previous transaction to determine usual expense", R.color.orange_800);
            }
        } else if (_spent_so_far < _typicalSolve) {
            //you are below typical
            bdx.getD().setTxtVariesTyical("Below Usual");
            bdx.getD().setTxtColor(R.color.green_700);
            bdx.getD().setTxtStatement("You are in the green zone. fantastic!!!\nContinue to keep your spending in check");
            showToolTips("You are in the green zone. fantastic!!!\nContinue to keep your spending in check", R.color.green_700);
            if (((_typicalSolve / 2) + (_typicalSolve / 4)) < _spent_so_far) {
                //change color to yellow
                bdx.getD().setTxtColor(R.color.orange_800);
                bdx.getD().setTxtStatement("You are close to typical, Easy with  your expenses");
                showToolTips("You are close to typical, Easy with  your expenses", R.color.orange_800);
            }
        }

       /*
        Select select3 = Select.from(MData.class).where(Condition.prop("TRX_STP").gt(passedMonth)).and(Condition.prop("TRX_MONTH").notEq(Tools.getMonth()));
        List<MData> mData3 = select3.list();
        for (MData t : mData3) {
            //check for debit data only
            if (t.getTrxType() == 2) {
                //add to map
                _moving_average3 += Float.parseFloat(t.getTrxValue());
            }
        }
        */

        //prepare chart
        pieEntries = new ArrayList<>();
        Iterator iterator = tmpValue.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            //put to chart
            pieEntries.add(new PieEntry(Float.parseFloat(entry.getValue().toString()), String.valueOf(entry.getKey())));
            _moving_average3 += Float.parseFloat(entry.getValue().toString());
            iterator.remove();
        }
        //solve for income this months as percentage
        float incom_percentage = (_spent_so_far < 1 ? 1 : _spent_so_far);
        incom_percentage = (_income_this_month / incom_percentage) * 100;
        //start assignment
        bdx.getD().setTxtSpentSoFar(Constants.getCurrency() + Tools.doCuurency(_spent_so_far)); //display spent so far
        bdx.getD().setTxtTypical2(Constants.getCurrency() + Tools.doCuurency(_moving_average3 / (_monthly_average_target > 4 ? 4 : _monthly_average_target))); //display typical 3 months
        bdx.getD().setTxtTypical(Constants.getCurrency() + Tools.doCuurency(_typicalSolve)); //display typical 3 months
        bdx.getD().setTxtBelowTypical(Constants.getCurrency() + Tools.doCuurency(Math.abs(_variesTypical))); //below typical
        bdx.getD().setTxtIncomeThisM(String.format("%.1f", incom_percentage) + "%"); //this months
        bdx.txtIncomeThis.setText("Total Income this month: " + Constants.getCurrency() + Tools.doCuurency(Math.abs(_income_this_month)));
        //add spent so far
        pieEntries.add(new PieEntry(_spent_so_far, Tools.getMonthAscAlpha(Tools.getMonth())));
        //gauge meter
        configGauge(_spent_so_far, Float.parseFloat(Tools.doFloat(_typicalSolve)));
    }


    //initialize components
    private boolean switcher = true;

    private void initComponents() {
        bdx.btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main();
            }
        });
        //switch between gauge
        bdx.btnSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switcher) {
                    bdx.gauge.setVisibility(View.VISIBLE);
                    bdx.gauge.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.zoom_in));
                    bdx.pieChart.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.zoom_out));
                    bdx.pieChart.setVisibility(View.GONE);
                    bdx.pieChart.clearAnimation();
                    switcher = false;
                } else {
                    bdx.gauge.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.zoom_out));
                    bdx.pieChart.setVisibility(View.VISIBLE);
                    bdx.pieChart.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.zoom_in));
                    bdx.gauge.setVisibility(View.GONE);
                    bdx.gauge.clearAnimation();
                    switcher = true;
                }
            }
        });

        //perform auto sync.
        bdx.menuSyncSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                syncAction();
            }
        });
        bdx.btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                syncAction();
            }
        });
        //show help
        bdx.menuHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.msgDialog(ctx, "Quick Help !", "1. Click on + icon to add new transaction\n\n2. Usual is same as last month expense\n\n3. Monthly Average is same as previous 4 months average\n\n4. Remember to press the synchronise button at the top right corner to keep your data safe on cloud.", R.drawable.ic_help, R.color.blue_700);
            }
        });
    }

    //auto sync.
    private void syncAction() {
        if (mProfile.getSms() == 0) {
            Tools.showToast(ctx, "Please enable sms auto sync.");
            return;
        }
        if (!isLogged) {
            Tools.showToast(ctx, "Please complete your profile to backup");
            return;
        }
        if (!Tools.isNetworkAvailable(ctx)) {
            Tools.showToast(ctx, "No Internet Access !");
            return;
        }
        bdx.menuSyncSms.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.rotate_infinite));
        bdx.btnSync.setEnabled(false);
        bdx.menuSyncSms.setEnabled(false);
        Synchronize.doBackupDb(ctx, mProfile.getEmail());
        Tools.showToast(ctx, "Synchronizing...");
        //post delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                bdx.btnSync.setEnabled(true);
                bdx.menuSyncSms.setEnabled(true);
                bdx.menuSyncSms.clearAnimation();
                Tools.showToast(ctx, "Sync completed.");
            }
        }, 5000);
        //backup system

    }

    //configure gauge
    private void configGauge(float value, float max) {
        /* deprecated
         Range rangeGreen = new Range();
         rangeGreen.setFrom(0);
         rangeGreen.setTo((max / 2));
         rangeGreen.setColor(getResources().getColor(R.color.green_500));

         Range rangeYellow = new Range();
         rangeYellow.setFrom((max / 2));
         rangeYellow.setTo((max - (max / 4)));
         rangeYellow.setColor(getResources().getColor(R.color.yellow_500));

         Range rangeRed = new Range();
         rangeRed.setFrom((max - (max / 4)));
         rangeRed.setTo(max);
         rangeRed.setColor(getResources().getColor(R.color.red_500));

         bdx.gauge.addRange(rangeGreen);
         bdx.gauge.addRange(rangeYellow);
         bdx.gauge.addRange(rangeRed);

         bdx.gauge.setMinValue(0);
         if (value > max) {
         bdx.gauge.setMaxValue((int) Float.parseFloat(Tools.doFloat(value)));
         Range rangeERed = new Range();
         rangeERed.setFrom((max));
         rangeERed.setTo(value);
         rangeERed.setColor(getResources().getColor(R.color.red_800));
         bdx.gauge.addRange(rangeERed);
         } else {
         bdx.gauge.setMaxValue((int) Float.parseFloat(Tools.doFloat(max)));
         }
         bdx.gauge.setValue((int) Float.parseFloat(Tools.doFloat(value)));
         bdx.gauge.setNeedleColor(getResources().getColor(R.color.orange_600));
         */
        //run percentage
        float percentage = (value / max) * 100;
        bdx.gauge.setSpeed(percentage);
    }

    //onResume for auto action
    @Override
    public void onResume() {
        super.onResume();
        //disturbed for account update
        //check sms settings
        MProfile check = MProfile.findById(MProfile.class, 1);
        if (check != null) {
            mProfile = check;
            isLogged = true;
        } else {
            mProfile.setSms(1);
            mProfile.setNotifications(1);
        }
    }
}
