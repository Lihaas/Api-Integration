package com.android.apiintegration;

import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {


    @GET("tasks")
    Call<JsonElement> getPosts(@Header("Authorization") String Header);

    @POST("users/login")
    Call<JsonElement> registerU(@Body HashMap registerApiPayload);




}
