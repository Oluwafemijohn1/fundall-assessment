package com.example.fundallassessment.remote.model

sealed class LoginResponse {
    data class LoginSuccessResponse(
        val success: Success? = null
    ) : LoginResponse()

    data class LoginErrorResponse(
        val error: Error? = null
    ) : LoginResponse()
}

data class LoginSuccessResponse(
    val success: Success? = null
)

data class Error(
    val code: String? = null,
    val message: String? = null,
    val status: String? = null
)

data class Success(
    val status: String? = null,
    val user: User? = null
)

data class User(
    val access_token: String? = null,
    val avatar: String? = null,
    val created_at: String? = null,
    val email: String? = null,
    val expires_at: String? = null,
    val firstname: String? = null,
    val id: Int? = null,
    val lastname: String? = null,
    val monthly_target: Int? = null,
    val token_type: String? = null,
    val updated_at: String? = null
)