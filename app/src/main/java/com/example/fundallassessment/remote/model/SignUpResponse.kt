package com.example.fundallassessment.remote.model

sealed class SignUpResponse {
    data class SignUpSuccessResponse(
        val success: Message? = null
    ):SignUpResponse()

    data class SignUpErrorResponse(
        val error: Message? = null
    )

}

data class Message(
    val message: String? = null
)



