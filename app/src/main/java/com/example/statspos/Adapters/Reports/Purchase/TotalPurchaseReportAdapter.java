package com.example.statspos.Adapters.Reports.Purchase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statspos.Models.Reports.Purchase.TotalPurchaseReport;
import com.example.statspos.R;
import com.example.statspos.databinding.TotalPurchaseReportHolderBinding;

import java.util.List;

public class TotalPurchaseReportAdapter extends RecyclerView.Adapter<TotalPurchaseReportAdapter.ViewHolder> {
    Context context;
    List<TotalPurchaseReport> list;

    public TotalPurchaseReportAdapter(Context context, List<TotalPurchaseReport> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.total_purchase_report_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TotalPurchaseReport totalPurchaseReport = list.get(position);

        holder.binding.invoiceNo.setText(totalPurchaseReport.getId());
        holder.binding.vendor.setText(totalPurchaseReport.getVendor());
        holder.binding.total.setText(totalPurchaseReport.getTotal());
        holder.binding.disc.setText(totalPurchaseReport.getDisc());
        holder.binding.purchaseOn.setText(totalPurchaseReport.getPurchaseOn());
        holder.binding.purchaseType.setText(totalPurchaseReport.getPurchaseType());
        holder.binding.isMopCashBank.setText(totalPurchaseReport.getIsMopCashBank());
        holder.binding.date.setText(totalPurchaseReport.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TotalPurchaseReportHolderBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = TotalPurchaseReportHolderBinding.bind(itemView);
        }
    }
}
