package com.andalus.lifeachievements.view_models

import androidx.lifecycle.*
import com.andalus.lifeachievements.LoginMutation
import com.andalus.lifeachievements.repositories.TokenRepository
import com.andalus.lifeachievements.enums.State
import com.andalus.lifeachievements.models.Response
import com.andalus.lifeachievements.networking.MutationRequest

class SignInViewModel(private val tokenRepository: TokenRepository) : ViewModel() {

    private val loginMutation = MutableLiveData<LoginMutation>()
    val response: LiveData<Response<LoginMutation.Data>> =
        Transformations.switchMap(loginMutation) {
            MutationRequest<LoginMutation.Data, LoginMutation.Variables, LoginMutation>().sendRequest(
                it
            )
        }

    val state = MediatorLiveData<State>()

    init {
        state.value = State.NormalState
        state.addSource(response) {
            if (it != null) {
                if (it.errors.isNotEmpty()) {
                    state.value = State.ErrorState
                } else {
                    state.value = State.SuccessState
                    if (it.data != null) {
                        tokenRepository.setToken(it.data.loginUser().token()!!)
                    }
                }
            }
        }
    }


    fun signIn(loginText: String, password: String) {
        state.value = State.LoadingState
        loginMutation.value = LoginMutation.builder().username(loginText).password(password).build()
    }
}
