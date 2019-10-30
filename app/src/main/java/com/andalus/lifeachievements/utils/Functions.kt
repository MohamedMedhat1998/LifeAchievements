package com.andalus.lifeachievements.utils

import androidx.lifecycle.MutableLiveData
import com.andalus.lifeachievements.models.Error
import com.apollographql.apollo.api.Response

class Functions<T> {

    val onFailure: (MutableList<Error>, MutableLiveData<List<Error>>, Exception) -> Unit =
        { errorList, resultLiveData, e ->

        }

    val onResponse: (MutableList<Error>, MutableLiveData<List<Error>>, Response<T>) -> Unit =
        { errorList, resultLiveData, response ->

        }
}