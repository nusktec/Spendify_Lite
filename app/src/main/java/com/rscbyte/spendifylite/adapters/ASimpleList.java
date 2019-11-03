package com.rscbyte.spendifylite.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.rscbyte.spendifylite.R;
import com.rscbyte.spendifylite.databinding.ListDataBinding;
import com.rscbyte.spendifylite.objects.OData;

import java.util.ArrayList;

public class ASimpleList extends RecyclerView.Adapter<ASimpleList.VH> {

    //public variables
    private ArrayList<OData> oDataArrayList;

    //initialize
    public ASimpleList(ArrayList<OData> oData) {
        this.oDataArrayList = oData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListDataBinding listDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_data, parent, false);
        return new VH(listDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        OData oDataTemp = oDataArrayList.get(position);
        holder.listDataBinding.setD(oDataTemp);
    }

    @Override
    public int getItemCount() {
        if (this.oDataArrayList == null) {
            return 0;
        }
        return this.oDataArrayList.size();
    }

    //view holder constructor
    static class VH extends RecyclerView.ViewHolder {

        //public binding data
        private ListDataBinding listDataBinding;

        VH(ListDataBinding dataBinding) {
            super(dataBinding.getRoot());
            this.listDataBinding = dataBinding;
        }
    }
}
