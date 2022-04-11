package com.example.statspos.Adapters.Reports.Purchase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statspos.Models.Reports.Purchase.ItemsPurchaseReport;
import com.example.statspos.R;
import com.example.statspos.databinding.ItemsPurchaseReportHolderBinding;

import java.util.List;

public class ItemsPurchaseReportAdapter extends RecyclerView.Adapter<ItemsPurchaseReportAdapter.ViewHolder> {

    Context context;
    List<ItemsPurchaseReport> list;

    public ItemsPurchaseReportAdapter(Context context, List<ItemsPurchaseReport> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_purchase_report_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemsPurchaseReport itemsPurchaseReport = list.get(position);

        holder.binding.date.setText(itemsPurchaseReport.getDate());
        holder.binding.itemname.setText(itemsPurchaseReport.getItemname());
        holder.binding.qty.setText(itemsPurchaseReport.getQty());
        holder.binding.crtn.setText(itemsPurchaseReport.getCrtn());
        holder.binding.cost.setText(itemsPurchaseReport.getCost());
        holder.binding.disc.setText(itemsPurchaseReport.getDisc());
        holder.binding.total.setText(itemsPurchaseReport.getTotal());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ItemsPurchaseReportHolderBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemsPurchaseReportHolderBinding.bind(itemView);
        }
    }
}
