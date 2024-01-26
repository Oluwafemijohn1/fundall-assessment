package com.example.fundallassessment.remote

import com.example.fundallassessment.remote.model.GetUserResponse
import com.example.fundallassessment.remote.model.LoginPayload
import com.example.fundallassessment.remote.model.LoginResponse
import com.example.fundallassessment.remote.model.LoginSuccessResponse
import com.example.fundallassessment.remote.model.SignUpPayload
import com.example.fundallassessment.remote.model.SignUpResponse
import com.example.fundallassessment.remote.model.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("register")
    suspend fun signUp(@Body user: SignUpPayload): SignUpResponse.SignUpSuccessResponse

    @POST("login")
    suspend fun login(@Body user: LoginPayload): LoginSuccessResponse

    @GET("base/profile")
    suspend fun getUser(@Header("Authorization") token: String): GetUserResponse

}