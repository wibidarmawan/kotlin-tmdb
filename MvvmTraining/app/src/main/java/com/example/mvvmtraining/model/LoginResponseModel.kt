package com.example.mvvmtraining.model

import com.google.gson.annotations.SerializedName

data class LoginResponseModel (
    val status: String,
    @SerializedName("userModelList")
    val userModel: UserModel
)