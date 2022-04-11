package com.example.statspos.Adapters.Items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statspos.Models.Items.Items;
import com.example.statspos.Models.Reports.StockReport;
import com.example.statspos.R;
import com.example.statspos.databinding.ItemsHolderBinding;
import com.example.statspos.databinding.StockReportHolderBinding;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    Context context;
    List<Items> list;
    OnClickListener onClickListener;

    public ItemsAdapter(Context context, List<Items> list, OnClickListener onClickListener) {
        this.context = context;
        this.list = list;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Items item = list.get(position);

        holder.binding.itemname.setText(item.getItemname());
        holder.binding.retail.setText(String.valueOf(item.getRetail()));
        holder.binding.wSale.setText(String.valueOf(item.getW_sale()));
        holder.binding.crtnRate.setText(String.valueOf(item.getCrtn_rate()));
        holder.binding.cost.setText(String.valueOf(item.getCost()));

        final int pos = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onClick(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemsHolderBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemsHolderBinding.bind(itemView);
        }
    }

    public interface OnClickListener{
        void onClick(int position);
    }
}
