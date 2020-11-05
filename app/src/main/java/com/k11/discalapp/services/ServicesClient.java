package com.k11.discalapp.services;

import com.k11.discalapp.config.Constants;
import com.k11.discalapp.dtos.AnswerLogin;
import com.k11.discalapp.dtos.AnswerRegistration;
import com.k11.discalapp.dtos.AnswerUserUpdate;
import com.k11.discalapp.dtos.UserRegistration;
import com.k11.discalapp.dtos.UserUpdate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ServicesClient {



    public static Call<AnswerRegistration> postUserRegistration(UserRegistration userRegistration) {
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.IpAdress)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create().asLenient());
        Retrofit retrofit = builder.build();

        return retrofit.create(ServicesInterface.class).postUserRegistration(userRegistration);
    }

    public static Call<AnswerLogin> loginCall(String userName, String password) {
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.IpAdress)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create().asLenient());
        Retrofit retrofit = builder.build();

        return retrofit.create(ServicesInterface.class).loginCall(userName, password);
    }

    public static Call<AnswerUserUpdate> updateCall(UserUpdate userUpdate) {
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.IpAdress)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create().asLenient());
        Retrofit retrofit = builder.build();

        return retrofit.create(ServicesInterface.class).updateCall(userUpdate);
    }

}
