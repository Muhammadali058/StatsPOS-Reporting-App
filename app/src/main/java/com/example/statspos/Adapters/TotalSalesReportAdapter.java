package com.example.statspos.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statspos.Models.TotalSalesReport;
import com.example.statspos.R;
import com.example.statspos.databinding.TotalSalesReportBinding;

import java.util.List;

public class TotalSalesReportAdapter extends RecyclerView.Adapter<TotalSalesReportAdapter.ViewHolder> {

    Context context;
    List<TotalSalesReport> list;

    public TotalSalesReportAdapter(Context context, List<TotalSalesReport> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.total_sales_report, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TotalSalesReport totalSalesReport = list.get(position);

        holder.binding.date.setText(totalSalesReport.getDate());
        holder.binding.invoiceNo.setText(totalSalesReport.getInvoiceNo());
        holder.binding.customer.setText(totalSalesReport.getCustomer());
        holder.binding.total.setText(totalSalesReport.getTotal());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TotalSalesReportBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = TotalSalesReportBinding.bind(itemView);
        }
    }
}
