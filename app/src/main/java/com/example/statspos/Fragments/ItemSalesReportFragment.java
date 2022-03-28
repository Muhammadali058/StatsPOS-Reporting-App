package com.example.statspos.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.statspos.R;
import com.example.statspos.databinding.FragmentByCustomerSalesReportBinding;
import com.example.statspos.databinding.FragmentByItemSalesReportBinding;

public class ItemSalesReportFragment extends Fragment {

    FragmentByItemSalesReportBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentByItemSalesReportBinding.bind(inflater.inflate(R.layout.fragment_by_item_sales_report, container, false));



        return binding.getRoot();
    }
}