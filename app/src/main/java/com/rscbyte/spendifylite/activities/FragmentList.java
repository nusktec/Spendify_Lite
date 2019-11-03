package com.rscbyte.spendifylite.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.rscbyte.spendifylite.R;
import com.rscbyte.spendifylite.adapters.ASimpleList;
import com.rscbyte.spendifylite.databinding.ActivityFragmentListBinding;
import com.rscbyte.spendifylite.objects.OData;

import java.util.ArrayList;
import java.util.List;

public class FragmentList extends Fragment {
    //make a public variables
    private ActivityFragmentListBinding bdx = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //ass layout binding to it parents
        bdx = DataBindingUtil.inflate(inflater, R.layout.activity_fragment_list, container, false);
        //establish data
        main();
        return bdx.getRoot();
    }

    //main methods
    public void main() {
        //make list item
        ArrayList<OData> oData = new ArrayList<>();
        for (int i = 0; i < 19; i++) {
            OData oData1 = new OData();
            oData1.setTitle("Debit/Credit");
            oData1.setDay("" + i);
            oData1.setDesc("Description " + i);
            oData1.setValue(i + "2,60" + i);
            oData1.setYear("200" + i);
            oData1.setMonth("Nov");
            oData.add(oData1);
        }
        //set recycler view
        ASimpleList aSimpleList = new ASimpleList(oData);
        bdx.listItemView.setAdapter(aSimpleList);
    }
}
