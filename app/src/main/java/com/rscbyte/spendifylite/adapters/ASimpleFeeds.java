package com.rscbyte.spendifylite.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.rscbyte.spendifylite.R;
import com.rscbyte.spendifylite.databinding.UtilAdvertsScrollerBinding;
import com.rscbyte.spendifylite.objects.OAdverts;

import java.util.List;

public class ASimpleFeeds extends RecyclerView.Adapter<ASimpleFeeds.VH> {

    //variable declarations
    private List<OAdverts> oAdverts;

    //initialize
    public ASimpleFeeds(List<OAdverts> oAdverts) {
        this.oAdverts = oAdverts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UtilAdvertsScrollerBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.util_adverts_scroller, null, false);
        return new VH(binding);
    }

    @Override
    public int getItemCount() {
        if (oAdverts == null) {
            return 0;
        } else {
            return oAdverts.size();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        OAdverts adverts = oAdverts.get(position);
        holder.binding.setD(adverts);
    }


    //holder class
    static class VH extends RecyclerView.ViewHolder {
        private UtilAdvertsScrollerBinding binding;

        VH(UtilAdvertsScrollerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
