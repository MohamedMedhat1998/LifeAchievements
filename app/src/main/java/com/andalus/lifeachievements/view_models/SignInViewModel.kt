package com.andalus.lifeachievements.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.andalus.lifeachievements.LoginMutation
import com.andalus.lifeachievements.models.Response
import com.andalus.lifeachievements.networking.MutationRequest

class SignInViewModel : ViewModel() {

    private val loginMutation = MutableLiveData<LoginMutation>()
    val response: LiveData<Response<LoginMutation.Data>> =
        Transformations.switchMap(loginMutation) {
            MutationRequest<LoginMutation.Data, LoginMutation.Variables, LoginMutation>().sendRequest(
                it
            )
        }

    fun signIn(loginText: String, password: String) {
        loginMutation.value = LoginMutation.builder().username(loginText).password(password).build()
    }
}
