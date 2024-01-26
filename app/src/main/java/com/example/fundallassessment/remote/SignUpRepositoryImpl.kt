package com.example.fundallassessment.remote

import android.util.Log
import com.example.fundallassessment.remote.model.GetUserResponse
import com.example.fundallassessment.remote.model.LoginPayload
import com.example.fundallassessment.remote.model.LoginResponse
import com.example.fundallassessment.remote.model.LoginSuccessResponse
import com.example.fundallassessment.remote.model.SignUpPayload
import com.example.fundallassessment.remote.model.SignUpResponse
import com.example.fundallassessment.remote.model.UserResponse
import com.example.fundallassessment.utils.Resource
import com.example.fundallassessment.utils.SafeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SignUpRepositoryImpl(
    private val apiService: ApiService,
) : SignUpRepository, SafeApiCall() {
    override suspend fun signUp(signUpDetails: SignUpPayload): Flow<Resource<SignUpResponse.SignUpSuccessResponse>> =
        flow {
            emit(Resource.Loading())
            Log.d("SignUpRepositoryImpl", "SignUpRepositoryImpl signUp: $signUpDetails")
            val response = safeApiCall { apiService.signUp(signUpDetails) }
            emit(response)
        }

    override suspend fun logIn(signUpDetails: LoginPayload): Flow<Resource<LoginSuccessResponse>> =
        flow {
            emit(Resource.Loading())
            val response = safeApiCall { apiService.login(signUpDetails) }
            emit(response)
        }

    override suspend fun getUser(token: String): Flow<Resource<GetUserResponse>> =
        flow {
            emit(Resource.Loading())
            val response = safeApiCall { apiService.getUser("Bearer $token") }
            emit(response)
        }
}
