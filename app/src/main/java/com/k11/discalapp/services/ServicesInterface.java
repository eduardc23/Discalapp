package com.k11.discalapp.services;

import com.k11.discalapp.config.Constants;
import com.k11.discalapp.dtos.AnswerLogin;
import com.k11.discalapp.dtos.AnswerRegistration;
import com.k11.discalapp.dtos.AnswerUserUpdate;
import com.k11.discalapp.dtos.UserRegistration;
import com.k11.discalapp.dtos.UserUpdate;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServicesInterface {

    @POST(Constants.REGISTER + Constants.APIVERSION)
    Call<AnswerRegistration> postUserRegistration(@Body UserRegistration userRegistration);

    @POST(Constants.LOGIN + Constants.APIVERSION)
    Call<AnswerLogin> loginCall(@Query("userName") String userName, @Query("password") String password);

    @POST(Constants.UPDATE + Constants.APIVERSION)
    Call<AnswerUserUpdate> updateCall(@Body UserUpdate userUpdate);
}
