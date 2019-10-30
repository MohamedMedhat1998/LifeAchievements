package com.andalus.lifeachievements.view_models

import androidx.lifecycle.*
import com.andalus.lifeachievements.CreateUserMutation
import com.andalus.lifeachievements.enums.State
import com.andalus.lifeachievements.models.Response
import com.andalus.lifeachievements.models.User
import com.andalus.lifeachievements.networking.MutationRequest

class SignUpViewModel : ViewModel() {

    private val mutation = MutableLiveData<CreateUserMutation>()
    val response: LiveData<Response<CreateUserMutation.Data>> =
        Transformations.switchMap(mutation) {
            MutationRequest<CreateUserMutation.Data, CreateUserMutation.Variables, CreateUserMutation>().sendRequest(
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
                }
            }
        }
    }


    fun signUp(user: User) {
        state.value = State.LoadingState
        mutation.value = CreateUserMutation.builder()
            .firstName(user.firstName)
            .lastName(user.lastName)
            .email(user.email)
            .phone(user.phone)
            .username(user.username)
            .password(user.password)
            .gender(user.gender)
            .build()

    }


}
