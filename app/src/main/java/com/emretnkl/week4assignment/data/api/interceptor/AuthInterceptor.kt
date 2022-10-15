package com.emretnkl.week4assignment.data.api.interceptor

import com.emretnkl.week4assignment.data.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val apiKeyRequest = originalRequest
            .newBuilder()
            .header("X-Api-Key",Constants.API_KEY)
            .build()
        return chain.proceed(apiKeyRequest)
    }
}