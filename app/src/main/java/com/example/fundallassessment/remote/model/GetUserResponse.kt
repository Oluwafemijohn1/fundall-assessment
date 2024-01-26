package com.example.fundallassessment.remote.model

sealed class UserResponse{
    data class GetUserResponse(
        val success: UserSuccess
    ): UserResponse()

    data class GetUserError(
        val code: String? = null,
        val error: String? = null
    ): UserResponse()
}

data class GetUserResponse(
    val success: UserSuccess
)



data class UserSuccess(
    val status: String? = null,
    val data: UserProfile? = null,
)

data class UserProfile(
    val id: Int? = null,
    val firstname: String? = null,
    val lastname: String? = null,
    val email: String? = null,
    val avatar: String? = null,
    val total_balance: String? = null,
    val income: String? = null,
    val spent: String? = null
)

