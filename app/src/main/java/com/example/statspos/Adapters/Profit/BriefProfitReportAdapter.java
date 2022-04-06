package com.example.statspos.Adapters.Profit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statspos.Models.Reports.Profit.BriefProfitReport;
import com.example.statspos.R;
import com.example.statspos.databinding.BriefProfitReportHolderBinding;

import java.util.List;

public class BriefProfitReportAdapter extends RecyclerView.Adapter<BriefProfitReportAdapter.ViewHolder> {

    Context context;
    List<BriefProfitReport> list;

    public BriefProfitReportAdapter(Context context, List<BriefProfitReport> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.brief_profit_report_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BriefProfitReport briefProfitReport = list.get(position);

        holder.binding.date.setText(briefProfitReport.getDate());
        holder.binding.description.setText(briefProfitReport.getDescription());
        holder.binding.grandProfit.setText(briefProfitReport.getGrandProfit());
        holder.binding.totalMargin.setText(briefProfitReport.getTotalMargin());
        holder.binding.totalBills.setText(briefProfitReport.getTotalBills());
        holder.binding.grandTotal.setText(briefProfitReport.getGrandTotal());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        BriefProfitReportHolderBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = BriefProfitReportHolderBinding.bind(itemView);
        }
    }
}
