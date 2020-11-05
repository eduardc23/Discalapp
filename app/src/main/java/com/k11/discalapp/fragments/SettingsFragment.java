package com.k11.discalapp.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.k11.discalapp.R;
import com.k11.discalapp.config.Constants;
import com.k11.discalapp.databinding.FragmentSettingsBinding;
import com.k11.discalapp.dtos.AnswerUserUpdate;
import com.k11.discalapp.dtos.UserUpdate;
import com.k11.discalapp.services.ServicesClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingsFragment extends Fragment implements View.OnClickListener {

    private FragmentSettingsBinding binding;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewMain;
        binding = FragmentSettingsBinding.inflate(getLayoutInflater());
        binding.btnSave.setOnClickListener(this);
        viewMain = binding.getRoot();
        setInfoUser();
        return viewMain;

    }

    private void setInfoUser() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String name = sharedPreferences.getString(Constants.NAME, null);
        String age = sharedPreferences.getString(Constants.AGE, null);
        if(name != null && age != null){
            binding.nameUser.setText(name);
            binding.username.setText(name);
            String edad = age+ " años";
            binding.ageUser.setText(edad);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_save) {
            if (validateUserName()) {
                if (validatePassword()) {
                    updateUser();
                } else {
                    showMessage("Las contraseñas no coinciden, por favor intentelo de nuevo.");
                }
            } else {
                showMessage("Por favor ingrese un nombre de usuario valido");

            }
        }
    }

    private boolean validatePassword() {
        boolean valid = false;
        if (binding.password.getText().toString().equals(binding.confirmPassword.getText().toString())) {
            valid = true;
        }
        return valid;
    }

    private boolean validateUserName() {
        boolean valid = false;
        if (binding.username.getText().toString().trim().length() > 0) {
            valid = true;
        }
        return valid;
    }

    private void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    private void updateUser() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String name = binding.username.getText().toString();
        String age = sharedPreferences.getString(Constants.AGE, null);
        String id = sharedPreferences.getString(Constants.ID, null);
        String password = binding.password.getText().toString();

        if(id!=null && age != null){
            final UserUpdate userUpdate = new UserUpdate(Integer.parseInt(id),name,name,Integer.parseInt(age),password,password);
            ServicesClient.updateCall(userUpdate).enqueue(new Callback<AnswerUserUpdate>() {
                @Override
                public void onResponse(Call<AnswerUserUpdate> call, Response<AnswerUserUpdate> response) {
                    if(response.isSuccessful()){
                        if(response.body()!=null){
                            if(response.code()==200){
                                updateFields(userUpdate);
                                showMessage("Actualización correcta de datos");
                            }else{
                                showMessage("Error al actualizar datos");
                            }
                        }
                    }else{
                        showMessage("Error al actualizar datos");
                    }
                }

                @Override
                public void onFailure(Call<AnswerUserUpdate> call, Throwable t) {
                }
            });
        }
    }

    private void updateFields(UserUpdate userUpdate) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constants.ID, String.valueOf(userUpdate.getId()));
        editor.putString(Constants.USER, userUpdate.getNombreCompleto());
        editor.putString(Constants.NAME, userUpdate.getNombreCompleto());
        editor.putString(Constants.AGE, String.valueOf(userUpdate.getEdad()));
        editor.apply();
    }
}