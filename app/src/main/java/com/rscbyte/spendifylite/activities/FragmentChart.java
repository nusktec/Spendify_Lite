package com.rscbyte.spendifylite.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    private int tmpSpentSoFar, tmpTypical, tmpBTypical;

    //prepare chart entries
    private void prepareChart() {
        //assign dictionary value holder
        Map<String, Integer> tmpValue = new HashMap<>();
        //get 3 months way back
        long passedMonth = Long.parseLong(Tools.getVariesTimeStamp(-3));
        /**
         * Upper algorithms is intent, will not be used for any at this point
         */
        //iterate last 3 month expense data
        int _spent_for_the_month = 0;
        String _month_name = "";
        Select select = Select.from(MData.class).where(Condition.prop("TRX_STP").gt(passedMonth));
        List<MData> mData = select.list();
        for (MData t : mData) {
            //check for debit data only
            if (t.getTrxType() == 2) {
                //add to map
                _spent_for_the_month += Integer.parseInt(t.getTrxValue());
                _month_name = Tools.getMonthAscNum(Integer.parseInt(t.getTrxMonth()));
                tmpValue.put(_month_name, _spent_for_the_month);
            }
        }

        int _spent_so_far = 0;
        //get this month account total expense
        Select select2 = Select.from(MData.class).where(Condition.prop("TRX_MONTH").eq(Tools.getMonth() + "")).and(Condition.prop("TRX_YEAR").eq(Tools.getYear() + ""));
        List<MData> mData2 = select2.list();
        for (MData t2 : mData2) {
            //work for the current month
            _spent_so_far += Integer.parseInt(t2.getTrxValue());
        }

        //start assignment
        bdx.getD().setTxtSpentSoFar(Constants.getCurrency() + Tools.doCuurency(_spent_so_far));

        //prepare chart
        pieEntries = new ArrayList<>();
        Iterator iterator = tmpValue.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            //put to chart
            pieEntries.add(new PieEntry(Float.parseFloat(entry.getValue().toString()), String.valueOf(entry.getKey())));
            iterator.remove();
        }

    }

    //initialize components
    private void initComponents() {
        bdx.btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Objects.requireNonNull(getActivity()).stopService(new Intent(ctx, SMSService.class));
                getActivity().startService(new Intent(ctx, SMSService.class));
                main();
            }
        });
    }
}
