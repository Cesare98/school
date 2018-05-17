package com.example.davide.esercizio_mongo_retrofit.network;

import com.example.davide.esercizio_mongo_retrofit.data_model.Response;
import com.example.davide.esercizio_mongo_retrofit.data_model.User;

import retrofit2.http.DELETE;
import rx.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitInterface {

    @POST("users")
    Observable<Response> register(@Body User user);

    @POST("authenticate")
    Observable<Response> login();

    @GET("users/{email}")
    Observable<User> getProfile(@Path("email") String email);

    @PUT("users/{email}")
    Observable<Response> changePassword(@Path("email") String email, @Body User user);

    @POST("users/{email}/password")
    Observable<Response> resetPasswordInit(@Path("email") String email);

    @POST("users/{email}/password")
    Observable<Response> resetPasswordFinish(@Path("email") String email, @Body User user);

    @DELETE("users/{email}/delete")
    Observable<Response> deleteAccount(@Path("email")String email);
}