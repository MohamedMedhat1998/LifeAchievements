package com.andalus.lifeachievements.networking

import com.andalus.lifeachievements.data.TokenRepository
import com.andalus.lifeachievements.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val tokenRepository: TokenRepository) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader(
                Constants.TOKEN_HEADER,
                "${Constants.TOKEN_BEARER} ${tokenRepository.getToken()}"
            ).build()
        return chain.proceed(newRequest)
    }

}