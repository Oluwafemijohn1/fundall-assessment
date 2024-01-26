package com.example.fundallassessment.remote

import com.example.fundallassessment.remote.model.GetUserResponse
import com.example.fundallassessment.remote.model.LoginPayload
import com.example.fundallassessment.remote.model.LoginResponse
import com.example.fundallassessment.remote.model.LoginSuccessResponse
import com.example.fundallassessment.remote.model.SignUpPayload
import com.example.fundallassessment.remote.model.SignUpResponse
import com.example.fundallassessment.remote.model.UserResponse
import com.example.fundallassessment.utils.Resource
import kotlinx.coroutines.flow.Flow
interface SignUpRepository {
    suspend fun signUp(signUpDetails: SignUpPayload): Flow<Resource<SignUpResponse.SignUpSuccessResponse>>
    suspend fun logIn(signUpDetails: LoginPayload): Flow<Resource<LoginSuccessResponse>>
    suspend fun getUser(token: String): Flow<Resource<GetUserResponse>>

}