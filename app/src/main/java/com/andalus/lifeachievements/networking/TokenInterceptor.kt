package com.andalus.lifeachievements.networking

import com.andalus.lifeachievements.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val token: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder().addHeader(Constants.TOKEN_HEADER, token).build()
        return chain.proceed(newRequest)
    }

}