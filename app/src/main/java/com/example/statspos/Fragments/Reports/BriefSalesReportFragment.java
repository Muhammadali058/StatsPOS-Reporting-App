package com.example.statspos.Fragments.Reports;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.statspos.R;
import com.example.statspos.databinding.FragmentBriefSalesReportBinding;

public class BriefSalesReportFragment extends Fragment {

    FragmentBriefSalesReportBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBriefSalesReportBinding.bind(inflater.inflate(R.layout.fragment_brief_sales_report, container, false));



        return binding.getRoot();
    }
}