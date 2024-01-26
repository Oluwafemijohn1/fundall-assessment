package com.example.fundallassessment.remote.model

data class SignUpPayload(
    val firstname: String? = null,
    val lastname: String? = null,
    val email: String? = null,
    val password: String? = null,
    val password_confirmation: String? = null
)
