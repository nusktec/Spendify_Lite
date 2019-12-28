package com.rscbyte.spendifylite.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
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
    private dataClicked dataClicked;

    //initialize single mode
    public ASimpleList(ArrayList<OData> oData) {
        this.oDataArrayList = oData;
        notifyDataSetChanged();
    }

    //initialize single mode
    public ASimpleList(ArrayList<OData> oData, dataClicked clicked) {
        this.oDataArrayList = oData;
        this.dataClicked = clicked;
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
        final OData oDataTemp = oDataArrayList.get(position);
        holder.listDataBinding.setD(oDataTemp);
        if (this.dataClicked != null) {
            //define click listener
            holder.listDataBinding.lytCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //return listener
                    dataClicked.onDataClicked(oDataTemp);
                }
            });
            if (oDataTemp.getmData().getTrxType() == 1) {
                holder.listDataBinding.trxIcon1.setVisibility(View.GONE);
            } else {
                holder.listDataBinding.trxIcon2.setVisibility(View.GONE);
            }
        }
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

    //public interface listener
    public interface dataClicked {
        void onDataClicked(OData oData);
    }
}
