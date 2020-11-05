package com.k11.discalapp.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.k11.discalapp.R;
import com.k11.discalapp.config.Constants;
import com.k11.discalapp.databinding.ActivityRegisterBinding;
import com.k11.discalapp.dtos.AnswerRegistration;
import com.k11.discalapp.dtos.UserRegistration;
import com.k11.discalapp.services.ServicesClient;
import com.k11.discalapp.ui.login.LoginActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final String[] years = {"Seleccione", "Seis (6)", "Siete (7)", "Ocho (8)", "Nueve (9)"};
    private ActivityRegisterBinding binding;
    private String year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.txtHaveAccount.setOnClickListener(this);
        binding.btnRegister.setOnClickListener(this);
        configurateSpinnerAge();
    }


    private void configurateSpinnerAge() {

        //Adapter para meses
        ArrayAdapter<String> adapterYears = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, years);
        binding.year.setAdapter(adapterYears);

        binding.year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //  year = (String) adapterView.getItemAtPosition(i);
                if (i == 1) {
                    year = "6";
                }
                if (i == 2) {
                    year = "7";
                }
                if (i == 3) {
                    year = "8";
                }
                if (i == 4) {
                    year = "9";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                if (validateFields()) {
                    register();
                }
                break;
            case R.id.txt_have_account:
                redirectLogin();
                break;
        }
    }

    private boolean validateFields() {
        boolean valid = false;
        if (validateUser()) {
            if (validateYear()) {
                if (validatePassowrd()) {
                    valid = true;
                } else {
                    showMessage("Las contraseñas no coinciden, por favor intentelo de nuevo.");
                }
            } else {
                showMessage("Por favor ingrese una edad");
            }
        } else {
            showMessage("Por favor ingrese un nombre de usuario valido");
        }
        return valid;
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private boolean validatePassowrd() {
        boolean valid = false;
        if (binding.password.getText().toString().equals(binding.confirmPassword.getText().toString())) {
            valid = true;
        }
        return valid;
    }

    private boolean validateYear() {
        boolean valid = false;
        if (binding.year.getSelectedItemPosition() != 0) {
            valid = true;
        }
        return valid;
    }

    private boolean validateUser() {
        boolean valid = false;
        if (binding.username.getText().toString().trim().length() > 0) {
            valid = true;
        }
        return valid;
    }

    private void redirectLogin() {
        Intent loginClass = new Intent(this, LoginActivity.class);
        loginClass.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginClass);
    }

    private void register() {
        UserRegistration userRegistration = new UserRegistration(binding.username.getText().toString(), binding.username.getText().toString(),
                Integer.parseInt(year), binding.password.getText().toString(), binding.confirmPassword.getText().toString());

        ServicesClient.postUserRegistration(userRegistration).enqueue(new Callback<AnswerRegistration>() {
            @Override
            public void onResponse(Call<AnswerRegistration> call, Response<AnswerRegistration> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.code() == 200) {
                            redirectHome();
                            saveUser(response.body());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AnswerRegistration> call, Throwable t) {
            }
        });
    }

    private void redirectHome() {
        Intent loginClass = new Intent(this, MainActivity.class);
        loginClass.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginClass);
    }

    private void saveUser(AnswerRegistration answerRegistration) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constants.ID, String.valueOf(answerRegistration.getId()));
        editor.putString(Constants.USER, answerRegistration.getNombreCompleto());
        editor.putString(Constants.NAME, answerRegistration.getNombreCompleto());
        editor.putString(Constants.AGE, String.valueOf(answerRegistration.getEdad()));
        editor.apply();
    }
}