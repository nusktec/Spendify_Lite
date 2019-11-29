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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.ekn.gruzer.gaugelibrary.Range;
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
import com.rscbyte.spendifylite.objects.OChartPage;
import com.rscbyte.spendifylite.services.SMSService;

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
                startActivity(new Intent(ctx, Profile.class));
            }
        });
        //set today's time
        bdx.txtTimeNow.setText("Summary as of " + Tools.getFormattedDateSimple());
        //print chart
        printChart();
        //initialize
        initComponents();
    }


    //charts properties
    private PieChart pieChart;
    private PieData pieData;
    private PieDataSet pieDataSet;
    private ArrayList pieEntries;

    //formulate data set
    private void printChart() {
        //initialize
        pieChart = bdx.pieChart;
        prepareChart();
        pieDataSet = new PieDataSet(pieEntries, "");
        pieData = new PieData(pieDataSet);
        pieChart.setCenterText("Spendify\nExpense");
        pieChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD);
        pieChart.setCenterTextColor(getResources().getColor(R.color.app_color_1));
        pieChart.setVerticalFadingEdgeEnabled(true);
        pieChart.setData(pieData);
        pieChart.setEntryLabelColor(getResources().getColor(R.color.grey_500));
        pieChart.setEntryLabelTypeface(Typeface.DEFAULT_BOLD);
        pieChart.setDrawSlicesUnderHole(true);
        pieChart.animateX(500);
        //apply colors
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        pieDataSet.setSliceSpace(2f);
        pieDataSet.setValueTextColor(R.color.app_color_1);
        pieDataSet.setValueTextSize(13f);
        pieDataSet.setSliceSpace(5f);
        pieDataSet.setValueLinePart1Length(.5f);
        pieDataSet.setValueLinePart2Length(.2f);
        pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setValueLineWidth(3f);
        pieDataSet.setUsingSliceColorAsValueLineColor(true);
        pieDataSet.setValueTypeface(Typeface.DEFAULT_BOLD);
        pieChart.setHoleColor(Color.WHITE);

    }

    //prepare chart entries
    private void prepareChart() {
        //assign dictionary value holder
        Map<String, Integer> tmpValue = new HashMap<>();
        //get 3 months way back
        long passedMonth = Long.parseLong(Tools.getVariesTimeStamp(-5));
        /**
         * Upper algorithms is intent, will not be used for any at this point
         */
        //iterate last 3 month expense data
        int _spent_for_the_month = 0;
        String _month_name = "";
        int month_changes = 0;
        int _moving_average = 0;
        Select select = Select.from(MData.class).where(Condition.prop("TRX_STP").gt(passedMonth)).and(Condition.prop("TRX_MONTH").notEq(Tools.getMonth())).limit(String.valueOf(Tools.getMonth()));
        List<MData> mData = select.list();
        for (MData t : mData) {
            //check for data different
            if (month_changes == 0) {
                month_changes = Integer.parseInt(t.getTrxMonth());
            }
            //reset as data changed
            if (month_changes != Integer.parseInt(t.getTrxMonth())) {
                _spent_for_the_month = 0;
                month_changes = 0;
            }
            //check for debit data only
            if (t.getTrxType() == 2) {
                //add to map
                _spent_for_the_month += Integer.parseInt(t.getTrxValue());
                _month_name = Tools.getMonthAscNum(Integer.parseInt(t.getTrxMonth()));
                tmpValue.put(_month_name, _spent_for_the_month);
                _moving_average += Integer.parseInt(t.getTrxValue());
            }
        }

        int _spent_so_far = 0;
        //get this month account total expense
        Select select2 = Select.from(MData.class).where(Condition.prop("TRX_MONTH").eq(Tools.getMonth() + "")).and(Condition.prop("TRX_YEAR").eq(Tools.getYear() + ""));
        List<MData> mData2 = select2.list();
        for (MData t2 : mData2) {
            //work for the current month
            if (t2.getTrxType() == 2) {
                _spent_so_far += Integer.parseInt(t2.getTrxValue());
            }
        }

        //solve for typical
        int _typicalSolve = (_moving_average / 3);
        //solve for differences
        int _variesTypical = _spent_so_far - _typicalSolve;
        //determine over spent or less
        if (_spent_so_far > _typicalSolve) {
            //you above typical
            bdx.getD().setTxtVariesTyical("Above Typical");
            bdx.getD().setTxtColor(R.color.red_800);
            //remark statement
            bdx.getD().setTxtStatement("Your account is above typical");
        } else if (_spent_so_far < _typicalSolve) {
            //you are below typical
            bdx.getD().setTxtVariesTyical("Below Typical");
            bdx.getD().setTxtColor(R.color.green_700);
            bdx.getD().setTxtStatement("Fantastic spending, It's your money");
            if ((_typicalSolve / 2) < _spent_so_far) {
                //change color to yellow
                bdx.getD().setTxtColor(R.color.yellow_700);
                bdx.getD().setTxtStatement("Careful spendify, you'r almost there...");
            }
        }


        //start assignment
        bdx.getD().setTxtSpentSoFar(Constants.getCurrency() + Tools.doCuurency(_spent_so_far)); //display spent so far
        bdx.getD().setTxtTypical(Constants.getCurrency() + Tools.doCuurency(_typicalSolve)); //display typical
        bdx.getD().setTxtBelowTypical(Constants.getCurrency() + Tools.doCuurency(Math.abs(_variesTypical)));
        //prepare chart
        pieEntries = new ArrayList<>();
        Iterator iterator = tmpValue.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            //put to chart
            pieEntries.add(new PieEntry(Float.parseFloat(entry.getValue().toString()), String.valueOf(entry.getKey())));
            iterator.remove();
        }
        //add spent so far
        pieEntries.add(new PieEntry(_spent_so_far, Tools.getMonthAscNum(Tools.getMonth())));
        //gauge meter
        configGauge(_spent_so_far, _typicalSolve);

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
                    bdx.gauge.setAnimation(AnimationUtils.loadAnimation(ctx, R.anim.zoom_out));
                    bdx.pieChart.setVisibility(View.VISIBLE);
                    bdx.pieChart.setAnimation(AnimationUtils.loadAnimation(ctx, R.anim.zoom_in));
                    bdx.gauge.setVisibility(View.GONE);
                    bdx.gauge.clearAnimation();
                    switcher = false;
                } else {
                    bdx.gauge.setVisibility(View.VISIBLE);
                    bdx.gauge.setAnimation(AnimationUtils.loadAnimation(ctx, R.anim.zoom_in));
                    bdx.pieChart.setAnimation(AnimationUtils.loadAnimation(ctx, R.anim.zoom_out));
                    bdx.pieChart.setVisibility(View.GONE);
                    bdx.pieChart.clearAnimation();
                    switcher = true;
                }
            }
        });

        //perform auto sync.
        bdx.menuSyncSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Objects.requireNonNull(getActivity()).stopService(new Intent(ctx, SMSService.class));
                getActivity().startService(new Intent(ctx, SMSService.class));
                bdx.menuSyncSms.setAnimation(AnimationUtils.loadAnimation(ctx, R.anim.rotate_infinite));
                bdx.menuSyncSms.setEnabled(false);
                //post delay
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bdx.menuSyncSms.setEnabled(true);
                        bdx.menuSyncSms.clearAnimation();
                    }
                }, 3000);
            }
        });
    }

    //configure gauge
    void configGauge(int value, int max) {
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
            bdx.gauge.setMaxValue(value);
            Range rangeERed = new Range();
            rangeERed.setFrom((max));
            rangeERed.setTo(value);
            rangeERed.setColor(getResources().getColor(R.color.red_800));
            bdx.gauge.addRange(rangeERed);
        } else {
            bdx.gauge.setMaxValue(max);
        }
        bdx.gauge.setValue(value);
        bdx.gauge.setNeedleColor(getResources().getColor(R.color.grey_300));
    }
}
