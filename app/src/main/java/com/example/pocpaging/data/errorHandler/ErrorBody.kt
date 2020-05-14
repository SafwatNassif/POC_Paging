package com.example.pocpaging.data.errorHandler

import com.google.gson.annotations.SerializedName

open class ErrorBody(
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("status_message")
    val statusMessage: String?,
    @SerializedName("success")
    val success: Boolean?
)