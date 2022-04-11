package com.example.statspos.Adapters.Reports.Purchase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statspos.Models.Reports.Purchase.BriefPurchaseReport;
import com.example.statspos.R;
import com.example.statspos.databinding.BriefPurchaseReportHolderBinding;

import java.util.List;

public class BriefPurchaseReportAdapter extends RecyclerView.Adapter<BriefPurchaseReportAdapter.ViewHolder> {

    Context context;
    List<BriefPurchaseReport> list;

    public BriefPurchaseReportAdapter(Context context, List<BriefPurchaseReport> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.brief_purchase_report_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BriefPurchaseReport briefPurchaseReport = list.get(position);

        holder.binding.date.setText(briefPurchaseReport.getDate());
        holder.binding.description.setText(briefPurchaseReport.getDescription());
        holder.binding.totalBills.setText(briefPurchaseReport.getTotalBills());
        holder.binding.grandTotal.setText(briefPurchaseReport.getGrandTotal());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        BriefPurchaseReportHolderBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = BriefPurchaseReportHolderBinding.bind(itemView);
        }
    }
}
