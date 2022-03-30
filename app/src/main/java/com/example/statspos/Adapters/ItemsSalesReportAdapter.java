package com.example.statspos.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statspos.HP;
import com.example.statspos.Models.Reports.ItemsSalesReport;
import com.example.statspos.R;
import com.example.statspos.databinding.ItemsSalesReportHolderBinding;

import java.util.List;

public class ItemsSalesReportAdapter extends RecyclerView.Adapter<ItemsSalesReportAdapter.ViewHolder> {

    Context context;
    List<ItemsSalesReport> list;

    public ItemsSalesReportAdapter(Context context, List<ItemsSalesReport> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_sales_report_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemsSalesReport itemsSalesReport = list.get(position);

        holder.binding.date.setText(itemsSalesReport.getDate());
        holder.binding.itemname.setText(itemsSalesReport.getItemname());
        holder.binding.qty.setText(itemsSalesReport.getQty());
        holder.binding.crtn.setText(itemsSalesReport.getCrtn());
        holder.binding.rate.setText(itemsSalesReport.getRate());
        holder.binding.crtnRate.setText(itemsSalesReport.getCrtnRate());
        holder.binding.disc.setText(itemsSalesReport.getDisc());
        holder.binding.total.setText(itemsSalesReport.getTotal());

        if(!HP.settings.isSaleCartons()){
            holder.binding.crtn.setVisibility(View.GONE);
            holder.binding.crtnRate.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ItemsSalesReportHolderBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemsSalesReportHolderBinding.bind(itemView);
        }
    }
}
