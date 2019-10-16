package com.andalus.lifeachievements.ui.sign_activity

import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.andalus.lifeachievements.CreateUserMutation
import com.andalus.lifeachievements.R
import com.andalus.lifeachievements.adapters.SignPagerAdapter
import com.andalus.lifeachievements.type.Gender
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import kotlinx.android.synthetic.main.activity_sign.*
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class SignActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_sign)

        vpSignActivity.adapter = SignPagerAdapter(supportFragmentManager)

        /*val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
        val apolloClient: ApolloClient =
            ApolloClient.builder()
                .serverUrl("https://protected-basin-72892.herokuapp.com/")
                .okHttpClient(okHttpClient)
                .build()

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
/*
* {
*   msg: "Internal Server Error.",
*   field: "IN"

* * }
* INVALID_TOKEN
* */
                    }
                }
                Log.wtf("DATA", response.data().toString())
                Log.d("ID", "${response.data()?.createUser()?.id()}")
            }

        })*/
    }
}