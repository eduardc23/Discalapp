package com.k11.discalapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.k11.discalapp.R;
import com.k11.discalapp.config.Constants;
import com.k11.discalapp.databinding.ActivitySplashBinding;
import com.k11.discalapp.ui.login.LoginActivity;


public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashBinding binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.transision);
        binding.imageViewSplash.startAnimation(animation);
        binding.imageViewSplashtitle.startAnimation(animation);
        Executetransition();

    }

    private void Executetransition() {
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (validateUser()) {
                        redirectHome();
                    } else {
                        redirectLogin();
                    }
                }
            }
        };
        timer.start();
    }

    private boolean validateUser() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String idGrupo = sharedPref.getString(Constants.USER, null);

        return idGrupo != null;
    }
    private void redirectLogin() {
        Intent splashIntro = new Intent(this, LoginActivity.class);
        splashIntro.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(splashIntro);
    }
    private void redirectHome() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
    }
}