package com.andalus.lifeachievements.networking

import androidx.lifecycle.MutableLiveData
import com.andalus.lifeachievements.models.Error
import com.andalus.lifeachievements.utils.Constants
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Mutation
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException

class MutationRequest<A : Operation.Data, B : Operation.Variables, T : Mutation<A, A, B>> {

    fun sendRequest(t: T): MutableLiveData<com.andalus.lifeachievements.models.Response<A>> {

        var errorList: MutableList<Error>?
        val resultLiveData = MutableLiveData<com.andalus.lifeachievements.models.Response<A>>()

        ApolloNetworkingClient.getInstance().mutate(t)
            .enqueue(object : ApolloCall.Callback<A>() {

                override fun onFailure(e: ApolloException) {
                    errorList = mutableListOf()
                    e.printStackTrace()
                    errorList!!.add(
                        Error(Constants.ERROR_FAILURE, e.message.toString())
                    )
                    resultLiveData.postValue(
                        com.andalus.lifeachievements.models.Response(
                            errorList!!,
                            null
                        )
                    )
                }

                override fun onResponse(response: Response<A>) {
                    errorList = mutableListOf()
                    if (response.hasErrors()) {
                        response.errors().forEach {
                            errorList!!.add(
                                Error(
                                    it.customAttributes()[Constants.ERROR_FIELD_KEY].toString(),
                                    it.customAttributes()[Constants.ERROR_MESSAGE_KEY].toString()
                                )
                            )
                        }
                    }
                    resultLiveData.postValue(
                        com.andalus.lifeachievements.models.Response(
                            errorList!!,
                            response.data()
                        )
                    )
                }
            })
        return resultLiveData
    }

}