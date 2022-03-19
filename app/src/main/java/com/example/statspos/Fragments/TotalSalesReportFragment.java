package com.example.statspos.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.statspos.R;
import com.example.statspos.databinding.FragmentTotalSalesReportBinding;

public class TotalSalesReportFragment extends Fragment {

    FragmentTotalSalesReportBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTotalSalesReportBinding.bind(inflater.inflate(R.layout.fragment_total_sales_report, container, false));



        return binding.getRoot();
    }
}