package com.example.statspos.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.statspos.R;
import com.example.statspos.databinding.FragmentByCategorySalesReportBinding;
import com.example.statspos.databinding.FragmentByVendorSalesReportBinding;

public class VendorSalesReportFragment extends Fragment {

    FragmentByVendorSalesReportBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentByVendorSalesReportBinding.bind(inflater.inflate(R.layout.fragment_by_vendor_sales_report, container, false));



        return binding.getRoot();
    }
}