package com.k11.discalapp.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.k11.discalapp.R;
import com.k11.discalapp.activities.MainActivity;
import com.k11.discalapp.activities.RegisterActivity;
import com.k11.discalapp.config.Constants;
import com.k11.discalapp.dtos.AnswerLogin;
import com.k11.discalapp.dtos.UserRegistration;
import com.k11.discalapp.services.ServicesClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private LoginViewModel loginViewModel;
    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        final TextView txtRegister = findViewById(R.id.txtRegister);
        txtRegister.setOnClickListener(this);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                updateUiWithUser();
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful

            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged();
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }

    private void updateUiWithUser() {
        if (validatePassword() && validateUserName()) {
            login();
        } else {
            showAlert();
        }
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:

                break;
            case R.id.txtRegister:
                redirectRegister();
                break;
        }
    }

    private void showAlert() {
        Toast.makeText(getApplicationContext(), "Por favor ingrese un usuario y una contraseña.", Toast.LENGTH_LONG).show();
    }

    private boolean validatePassword() {
        boolean valid = false;
        if (passwordEditText.getText().toString().trim().length() > 0) {
            valid = true;
        }
        return valid;
    }

    private boolean validateUserName() {
        boolean valid = false;
        String username = usernameEditText.getText().toString();
        if (username.trim().length() > 0) {
            valid = true;
        }
        return valid;
    }

    private void redirectRegister() {
        Intent loginClass = new Intent(this, RegisterActivity.class);
        loginClass.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginClass);
    }

    private void login() {

        ServicesClient.loginCall(usernameEditText.getText().toString(), passwordEditText.getText().toString()).enqueue(new Callback<AnswerLogin>() {
            @Override
            public void onResponse(Call<AnswerLogin> call, Response<AnswerLogin> response) {
                if (response.code() == 400) {
                    Toast.makeText(getApplicationContext(), "Error la iniciar sesión", Toast.LENGTH_LONG).show();
                }
                if (response.code() == 200) {
                    if (response.body() != null) {
                        saveUser(response.body());
                        redirectHome();
                    }

                }
            }

            @Override
            public void onFailure(Call<AnswerLogin> call, Throwable t) {

            }
        });

    }

    private void redirectHome() {
        Intent loginClass = new Intent(this, MainActivity.class);
        loginClass.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginClass);
    }

    private void saveUser(AnswerLogin answerLogin) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constants.ID, String.valueOf(answerLogin.getId()));
        editor.putString(Constants.USER, answerLogin.getNombreUsuario());
        editor.putString(Constants.NAME, answerLogin.getNombreCompleto());
        editor.putString(Constants.AGE, String.valueOf(answerLogin.getEdad()));
        editor.apply();
    }
}