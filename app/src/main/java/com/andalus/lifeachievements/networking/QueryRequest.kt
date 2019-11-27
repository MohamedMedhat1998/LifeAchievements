package com.andalus.lifeachievements.networking

import androidx.lifecycle.MutableLiveData
import com.andalus.lifeachievements.repositories.TokenRepository
import com.andalus.lifeachievements.models.Error
import com.andalus.lifeachievements.utils.Constants
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException

class QueryRequest<A : Operation.Data, B : Operation.Variables, T : Query<A, A, B>>(private val tokenRepository: TokenRepository? = null) {

    fun sendRequest(t: T): MutableLiveData<com.andalus.lifeachievements.models.Response<A>> {

        var errorList: MutableList<Error>?
        val resultLiveData = MutableLiveData<com.andalus.lifeachievements.models.Response<A>>()
        val client =
            if (tokenRepository == null)
                ApolloNetworkingClient.getInstance()
            else
                ApolloNetworkingClient.getInstance(tokenRepository)

        client.query(t)
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