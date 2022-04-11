package com.example.statspos.Adapters.Reports.Profit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statspos.Models.Reports.Profit.TotalProfitReport;
import com.example.statspos.R;
import com.example.statspos.databinding.TotalProfitReportHolderBinding;

import java.util.List;

public class TotalProfitReportAdapter extends RecyclerView.Adapter<TotalProfitReportAdapter.ViewHolder> {

    Context context;
    List<TotalProfitReport> list;

    public TotalProfitReportAdapter(Context context, List<TotalProfitReport> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.total_profit_report_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TotalProfitReport totalProfitReport = list.get(position);

        holder.binding.invoiceNo.setText(totalProfitReport.getId());
        holder.binding.total.setText(totalProfitReport.getTotal());
        holder.binding.profit.setText(totalProfitReport.getProfit());
        holder.binding.customer.setText(totalProfitReport.getCustomer());
        holder.binding.date.setText(totalProfitReport.getDate());
        holder.binding.margin.setText(totalProfitReport.getMargin() + "%");
        holder.binding.disc.setText(totalProfitReport.getDisc());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TotalProfitReportHolderBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = TotalProfitReportHolderBinding.bind(itemView);
        }
    }
}
