package com.example.pocpaging.data.errorHandler

import okhttp3.Interceptor
import okhttp3.Response

class ErrorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originResponse = chain.proceed(chain.request())
        if (shouldLogout(originResponse)) {
            return Response.Builder().build()
        }

        return originResponse
    }

    private fun shouldLogout(originResponse: Response): Boolean {
        if (originResponse.isSuccessful)
            return false

        // 401 and auth token means that we need to logout
        return originResponse.code == 401
    }



}