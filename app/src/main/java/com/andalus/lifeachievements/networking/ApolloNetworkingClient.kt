package com.andalus.lifeachievements.networking

import com.andalus.lifeachievements.data.TokenRepository
import com.andalus.lifeachievements.utils.Constants.Companion.SERVER_URL
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.CacheHeaders
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class ApolloNetworkingClient {
    companion object {

        private var normalInstance: ApolloClient? = null
        private var headerInstance: ApolloClient? = null
        private val loggingInterceptor = HttpLoggingInterceptor()

        init {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }

        @JvmStatic
        fun getInstance(): ApolloClient {
            if (normalInstance == null) {
                val okHttpClient =
                    OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
                normalInstance = ApolloClient.builder()
                    .serverUrl(SERVER_URL)
                    .okHttpClient(okHttpClient)
                    .build()
            }
            return normalInstance!!
        }

        @JvmStatic
        fun getInstance(tokenRepository: TokenRepository): ApolloClient {
            if (headerInstance == null) {
                val okHttpClient =
                    OkHttpClient.Builder()
                        .addInterceptor(TokenInterceptor(tokenRepository))
                        .addInterceptor(loggingInterceptor).build()
                headerInstance = ApolloClient.builder()
                    .serverUrl(SERVER_URL)
                    .okHttpClient(okHttpClient)
                    .build()
            }
            return headerInstance!!
        }

    }


}