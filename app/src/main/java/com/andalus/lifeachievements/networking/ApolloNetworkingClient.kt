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


    /*fun test() {


        val request = apolloClient.mutate(
            CreateUserMutation.builder()
                .firstName("Mohamed")
                .lastName("Salah")
                .email("abosalah@gmail.com")
                .phone("01063863299")
                .username("salah95")
                .gender(Gender.MALE)
                .password("123456789")
                .build()
        )

        request.enqueue(object : ApolloCall.Callback<CreateUserMutation.Data>() {
            override fun onFailure(e: ApolloException) {
                e.printStackTrace()
                Log.wtf("onFailure", e.message)
            }

            override fun onResponse(response: Response<CreateUserMutation.Data>) {
                Log.d("RESPONSE", response.toString())
                Log.d("HAS ERRORS", response.hasErrors().toString())

                if (response.hasErrors()) {
                    response.errors().forEach {
                        Log.wtf("ERROR", it.customAttributes()["msg"].toString())

                    }
                }
                Log.wtf("DATA", response.data().toString())
                Log.d("ID", "${response.data()?.createUser()?.errors()}")
            }

        })

    }*/


}