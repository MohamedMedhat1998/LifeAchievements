package com.andalus.lifeachievements.view_models

import androidx.lifecycle.*
import com.andalus.lifeachievements.VerifyMutation
import com.andalus.lifeachievements.enums.State
import com.andalus.lifeachievements.models.Response
import com.andalus.lifeachievements.networking.MutationRequest

class VerificationViewModel : ViewModel() {

    private val verificationMutation = MutableLiveData<VerifyMutation>()
    val response: LiveData<Response<VerifyMutation.Data>> =
        Transformations.switchMap(verificationMutation) {
            MutationRequest<VerifyMutation.Data, VerifyMutation.Variables, VerifyMutation>().sendRequest(
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

    fun verify(email: String, code: String) {
        state.value = State.LoadingState
        verificationMutation.value = VerifyMutation(email, code)
    }
}