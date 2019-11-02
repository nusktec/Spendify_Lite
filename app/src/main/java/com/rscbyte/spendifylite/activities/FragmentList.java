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
import com.rscbyte.spendifylite.databinding.ActivityFragmentListBinding;

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

    }
}
