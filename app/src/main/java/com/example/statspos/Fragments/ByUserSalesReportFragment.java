package com.example.statspos.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.statspos.R;
import com.example.statspos.databinding.FragmentByCustomerSalesReportBinding;
import com.example.statspos.databinding.FragmentByUserSalesReportBinding;

public class ByUserSalesReportFragment extends Fragment {

    FragmentByUserSalesReportBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentByUserSalesReportBinding.bind(inflater.inflate(R.layout.fragment_by_user_sales_report, container, false));



        return binding.getRoot();
    }
}