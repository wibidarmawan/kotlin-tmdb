package com.example.mvvmtraining.api

import com.example.mvvmtraining.model.LoginResponseModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @GET("login")
    fun login(@Query("email") email: String,
              @Query("password") password: String): Single<LoginResponseModel>

    @POST("register")
    fun register(@Query("email") email:String,
                 @Query("name") name: String,
                 @Query("dob") dob: String,
                 @Query("address") address: String,
                 @Query("password") password: String): Single<LoginResponseModel>
}