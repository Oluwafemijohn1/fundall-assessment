package com.example.fundallassessment.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fundallassessment.local.DataStoreRepository
import com.example.fundallassessment.remote.SignUpRepository
import com.example.fundallassessment.remote.model.GetUserResponse
import com.example.fundallassessment.remote.model.LoginPayload
import com.example.fundallassessment.remote.model.LoginResponse
import com.example.fundallassessment.remote.model.LoginSuccessResponse
import com.example.fundallassessment.remote.model.SignUpPayload
import com.example.fundallassessment.remote.model.SignUpResponse
import com.example.fundallassessment.remote.model.UserResponse
import com.example.fundallassessment.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val signUpRepository: SignUpRepository
): ViewModel() {

    private val _signUpUiState = MutableStateFlow<Resource<SignUpResponse.SignUpSuccessResponse>>(Resource.Empty())
    val signUpUiState = _signUpUiState.asStateFlow()

    private val _loginUiState = MutableStateFlow<Resource<LoginSuccessResponse>>(Resource.Empty())
    val loginUiState = _loginUiState.asStateFlow()

    private val _userUiState = MutableStateFlow<Resource<GetUserResponse>>(Resource.Empty())
    val userUiState = _userUiState.asStateFlow()

    private val _userEmailUiState = MutableStateFlow("")
    val userEmailUiState = _userEmailUiState.asStateFlow()

    private val _userTokenUiState = MutableStateFlow("")
    val userTokenUiState = _userTokenUiState.asStateFlow()

    fun signUp(payload: SignUpPayload){
        _signUpUiState.value = Resource.Loading(null, "Loading...")
        viewModelScope.launch{
            Log.d("TAG", "AuthViewModel signUp: $payload")
            signUpRepository.signUp(payload).collect {
                _signUpUiState.value = it
            }
        }
    }

    fun login(payload: LoginPayload){
        _loginUiState.value = Resource.Loading(null, "Loading...")
        viewModelScope.launch(Dispatchers.IO){
            signUpRepository.logIn(payload).collect {
                _loginUiState.value = it
            }
        }
    }

    fun getUser(){
        _userUiState.value = Resource.Loading(null, "Loading...")
        viewModelScope.launch(Dispatchers.IO){
            Log.d("AuthViewModel", "AuthViewModel getUser: ")
            _loginUiState.value.data?.success?.user?.access_token?.let {
                signUpRepository.getUser(it).collect {
                    _userUiState.value = it
                }
            }
        }
    }

    fun putString(key: String, value: String) {
        viewModelScope.launch {
            dataStoreRepository.putString(key, value)
        }
    }

    fun getString(key: String){
        viewModelScope.launch {
            dataStoreRepository.getString(key)?.let {
                Log.d("AuthViewModel", "AuthViewModel login: $it")
                _userEmailUiState.value = it
            }
        }
    }
}