package com.example.statspos.Adapters.Reports.Accounts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statspos.Models.Reports.Accounts.Ledger;
import com.example.statspos.R;
import com.example.statspos.databinding.JournalReportHolderBinding;

import java.util.List;

public class JournalReportAdapter extends RecyclerView.Adapter<JournalReportAdapter.ViewHolder> {

    Context context;
    List<Ledger> list;

    public JournalReportAdapter(Context context, List<Ledger> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.journal_report_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ledger ledger = list.get(position);

        holder.binding.date.setText(ledger.getDate());
        holder.binding.naration.setText(ledger.getNaration());
        holder.binding.debit.setText(ledger.getDebit());
        holder.binding.credit.setText(ledger.getCredit());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        JournalReportHolderBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = JournalReportHolderBinding.bind(itemView);
        }
    }
}
