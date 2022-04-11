package com.example.statspos.Adapters.Reports.Accounts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statspos.Models.Reports.Accounts.IncomeStatement;
import com.example.statspos.R;
import com.example.statspos.databinding.IncomeStatementHolderBinding;

import java.util.List;

public class IncomeStatementAdapter extends RecyclerView.Adapter<IncomeStatementAdapter.ViewHolder> {

    Context context;
    List<IncomeStatement> list;

    public IncomeStatementAdapter(Context context, List<IncomeStatement> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.income_statement_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IncomeStatement incomeStatement = list.get(position);

        holder.binding.expense.setText(incomeStatement.getNaration());
        holder.binding.amount1.setText(incomeStatement.getExpense());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        IncomeStatementHolderBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = IncomeStatementHolderBinding.bind(itemView);
        }
    }
}
