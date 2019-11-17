package com.andalus.lifeachievements.networking

import com.andalus.lifeachievements.utils.Constants.Companion.SERVER_URL
import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class ApolloNetworkingClient {

    companion object {

        private var instance: ApolloClient? = null

        @JvmStatic
        fun getInstance(): ApolloClient {

            if (instance == null) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
                instance = ApolloClient.builder()
                    .serverUrl(SERVER_URL)
                    .okHttpClient(okHttpClient)
                    .build()
            }
            return instance!!
        }
    }

}