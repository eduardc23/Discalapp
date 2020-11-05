package com.k11.discalapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.k11.discalapp.R;
import com.k11.discalapp.databinding.ActivityMainBinding;
import com.k11.discalapp.fragments.DyscalculiasFragment;
import com.k11.discalapp.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        binding.navView.setItemIconTintList(null);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new DyscalculiasFragment()).commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @SuppressLint("ResourceType")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            Menu menu = binding.navView.getMenu();
            switch (item.getItemId()) {
                case (R.id.pruebas):
                    selectedFragment = new DyscalculiasFragment();
                    break;
                case (R.id.ajustes):
                    selectedFragment = new SettingsFragment();
                    break;
            }
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, selectedFragment).commit();
            }
            return true;
        }
    };
}