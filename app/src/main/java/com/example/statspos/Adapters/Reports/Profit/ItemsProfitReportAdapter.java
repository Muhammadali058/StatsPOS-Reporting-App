package com.example.statspos.Adapters.Reports.Profit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statspos.HP;
import com.example.statspos.Models.Reports.Profit.ItemsProfitReport;
import com.example.statspos.R;
import com.example.statspos.databinding.ItemsProfitReportHolderBinding;

import java.util.List;

public class ItemsProfitReportAdapter extends RecyclerView.Adapter<ItemsProfitReportAdapter.ViewHolder> {

    Context context;
    List<ItemsProfitReport> list;

    public ItemsProfitReportAdapter(Context context, List<ItemsProfitReport> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_profit_report_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemsProfitReport itemsProfitReport = list.get(position);

        holder.binding.itemname.setText(itemsProfitReport.getItemname());
        holder.binding.cost.setText(itemsProfitReport.getCost());
        holder.binding.qty.setText(itemsProfitReport.getQty());
        holder.binding.crtn.setText(itemsProfitReport.getCrtn());
        holder.binding.total.setText(itemsProfitReport.getTotal());
        holder.binding.profit.setText(itemsProfitReport.getProfit());
        holder.binding.margin.setText(itemsProfitReport.getMargin() + "%");
        holder.binding.date.setText(itemsProfitReport.getDate());

        if(!HP.settings.isSaleCartons()){
            holder.binding.crtn.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ItemsProfitReportHolderBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemsProfitReportHolderBinding.bind(itemView);
        }
    }
}
