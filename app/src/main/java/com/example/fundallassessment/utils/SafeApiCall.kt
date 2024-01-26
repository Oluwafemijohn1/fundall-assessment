package com.example.fundallassessment.utils

import android.database.sqlite.SQLiteException
import com.example.fundallassessment.utils.Constants.NETWORK_ERROR_MESSAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import java.net.UnknownHostException

abstract class SafeApiCall {
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(
                    apiCall.invoke()
                )
            } catch (throwable: Throwable) {
                when (throwable) {

                    is SQLiteException -> Resource.Error(
                        false,
                        message = "Error reading from the caching source. Please try again",
                        errorBody = null
                    )
                    is UnknownHostException -> Resource.Error(
                        true,
                        null,
                        null,
                        message = "No Internet Connection, Please check your internet connection"
                    )
                    is IOException -> Resource.Error(
                        true,
                        null,
                        null,
                        message = "Something went wrong. Please retry."
                    )
                    is HttpException -> {
                        if (throwable.code() == 401) {
                            Resource.Error(
                                false,
                                message = "Please check the details entered and retry",
                                errorBody = null
                            )
                        } else {
                            Resource.Error(
                                false,
                                null,
                                message = "Please check your input and try again"
                            )
                        }
                    }
                    else -> {
                        Resource.Error(
                            true,
                            null,
                            null,
                            message = throwable.message ?: NETWORK_ERROR_MESSAGE
                        )
                    }
                }
            }
        }
    }
}
