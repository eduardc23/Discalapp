package com.k11.discalapp.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.k11.discalapp.R;
import com.k11.discalapp.config.Constants;
import com.k11.discalapp.databinding.FragmentDyscalculiasBinding;


public class DyscalculiasFragment extends Fragment {

    private FragmentDyscalculiasBinding binding;

    public DyscalculiasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewMain;
        binding = FragmentDyscalculiasBinding.inflate(getLayoutInflater());
        viewMain = binding.getRoot();
        setInfotmationStudent();
        return viewMain;
        //return inflater.inflate(R.layout.fragment_dyscalculias, container, false);
    }

    private void setInfotmationStudent() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String name = sharedPreferences.getString(Constants.NAME, null);
        String age = sharedPreferences.getString(Constants.AGE, null);

        if(name != null){
            binding.name.setText(name);
        }
        if(age != null){
            String edad = age + " años";
            binding.age.setText(edad);
        }
    }
}