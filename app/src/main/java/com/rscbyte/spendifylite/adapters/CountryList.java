package com.rscbyte.spendifylite.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.rscbyte.spendifylite.R;
import com.rscbyte.spendifylite.databinding.CustomSpinnerListBinding;

public class CountryList extends BaseAdapter {

    private Context context;
    private String[] countries_name;
    private String[] countries_curr;
    private TypedArray countries_flags;
    private CustomSpinnerListBinding binding;
    private LayoutInflater inflater;
    private CallBacks callBacks;

    public CountryList(Context ctx) {
        this.context = ctx;
        this.callBacks = callBacks;
        //assign others
        this.countries_name = context.getResources().getStringArray(R.array.countries_name_array);
        this.countries_curr = context.getResources().getStringArray(R.array.countries_curr_array);
        this.countries_flags = context.getResources().obtainTypedArray(R.array.countries_flags_array);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return countries_name.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        CustomSpinnerListBinding bn = DataBindingUtil.inflate(inflater, R.layout.custom_spinner_list, null, false);
        bn.imgFlag.setImageDrawable(countries_flags.getDrawable(i));
        bn.txtCountry.setText(countries_name[i]);
        return bn.getRoot();
    }

    //onclick listener
    public interface CallBacks {
        void onChoosen(String symbol, String country, int country_num);
    }
}
