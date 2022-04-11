package com.example.statspos.Adapters.Reports.Sales;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statspos.Models.Reports.Sales.BriefSalesReport;
import com.example.statspos.R;
import com.example.statspos.databinding.BriefSalesReportHolderBinding;

import java.util.List;

public class BriefSalesReportAdapter extends RecyclerView.Adapter<BriefSalesReportAdapter.ViewHolder> {

    Context context;
    List<BriefSalesReport> list;

    public BriefSalesReportAdapter(Context context, List<BriefSalesReport> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.brief_sales_report_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BriefSalesReport briefSalesReport = list.get(position);

        holder.binding.date.setText(briefSalesReport.getDate());
        holder.binding.description.setText(briefSalesReport.getDescription());
        holder.binding.totalBills.setText(briefSalesReport.getTotalBills());
        holder.binding.grandTotal.setText(briefSalesReport.getGrandTotal());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        BriefSalesReportHolderBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = BriefSalesReportHolderBinding.bind(itemView);
        }
    }
}
