package com.example.statspos.Adapters.Reports.Accounts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statspos.Models.Reports.Accounts.DebtorsCreditors;
import com.example.statspos.R;
import com.example.statspos.databinding.DebtorsCreditorsReportHolderBinding;

import java.util.List;

public class DebtorsCreditorsAdapter extends RecyclerView.Adapter<DebtorsCreditorsAdapter.ViewHolder> {

    Context context;
    List<DebtorsCreditors> list;

    public DebtorsCreditorsAdapter(Context context, List<DebtorsCreditors> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.debtors_creditors_report_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DebtorsCreditors debtorsCreditors = list.get(position);

        holder.binding.accountName.setText(debtorsCreditors.getAccountName());
        holder.binding.balance.setText(debtorsCreditors.getBalance());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        DebtorsCreditorsReportHolderBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DebtorsCreditorsReportHolderBinding.bind(itemView);
        }
    }
}
