package com.example.statspos.Adapters.Stock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statspos.Models.Reports.StockReport;
import com.example.statspos.R;
import com.example.statspos.databinding.StockReportHolderBinding;

import java.util.List;

public class StockReportAdapter extends RecyclerView.Adapter<StockReportAdapter.ViewHolder> {

    Context context;
    List<StockReport> list;

    public StockReportAdapter(Context context, List<StockReport> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stock_report_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StockReport stockReport = list.get(position);

        holder.binding.itemname.setText(stockReport.getItemname());
        holder.binding.stockPcs.setText(stockReport.getStock_pcs());
        holder.binding.stockCrtn.setText(stockReport.getStock_crtn());
        holder.binding.rate.setText(stockReport.getRate());
        holder.binding.stockValue.setText(stockReport.getStockValue());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        StockReportHolderBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = StockReportHolderBinding.bind(itemView);
        }
    }
}
