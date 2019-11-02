package com.rscbyte.spendifylite.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.rscbyte.spendifylite.R;
import com.rscbyte.spendifylite.Utils.Tools;
import com.rscbyte.spendifylite.databinding.ActivityFragmentChartBinding;

import java.util.ArrayList;

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
        pieChart.setCenterText("Spendify\nReports");
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
        pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(1000, "Jan"));
        pieEntries.add(new PieEntry(2450, "Feb"));
        pieEntries.add(new PieEntry(2220, "Mar"));
        pieEntries.add(new PieEntry(800, "Apr"));
    }
}
