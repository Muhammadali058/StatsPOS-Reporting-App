package com.example.statspos.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.statspos.R;
import com.example.statspos.databinding.FragmentByCustomerSalesReportBinding;

public class ByCustomerSalesReportFragment extends Fragment {

    FragmentByCustomerSalesReportBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentByCustomerSalesReportBinding.bind(inflater.inflate(R.layout.fragment_by_customer_sales_report, container, false));



        return binding.getRoot();
    }
}