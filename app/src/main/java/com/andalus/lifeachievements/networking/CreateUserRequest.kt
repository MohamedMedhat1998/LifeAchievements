package com.andalus.lifeachievements.networking

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.andalus.lifeachievements.CreateUserMutation
import com.andalus.lifeachievements.utils.Constants
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException

class CreateUserRequest {

    companion object {


        fun createUser(mutation: CreateUserMutation): MutableLiveData<List<Pair<String, String>>> {

            Log.d("create User", "Create User")

            var errorList: List<Pair<String, String>>? = null
            val resultLiveData = MutableLiveData<List<Pair<String, String>>>()

            ApolloNetworkingClient.getInstance().mutate(mutation)
                .enqueue(object : ApolloCall.Callback<CreateUserMutation.Data>() {

                    override fun onFailure(e: ApolloException) {
                        e.printStackTrace()
                        errorList = mutableListOf()
                        (errorList as MutableList<Pair<String, String>>).add(
                            Pair(
                                Constants.ERROR_FAILURE,
                                e.message.toString()
                            )
                        )
                        Log.d("create User", "onFailure")
                        resultLiveData.postValue(errorList)
                    }

                    override fun onResponse(response: Response<CreateUserMutation.Data>) {
                        Log.d("create User", "onResponse")
                        errorList = mutableListOf()
                        if (response.hasErrors()) {
                            response.errors().forEach {
                                (errorList as MutableList<Pair<String, String>>).add(
                                    Pair(
                                        it.customAttributes()[Constants.ERROR_FIELD_KEY].toString(),
                                        it.customAttributes()[Constants.ERROR_MESSAGE_KEY].toString()
                                    )
                                )
                                Log.d("create User", it.toString())
                            }
                        }
                        resultLiveData.postValue(errorList)
                    }
                })
            return resultLiveData
        }

    }


}