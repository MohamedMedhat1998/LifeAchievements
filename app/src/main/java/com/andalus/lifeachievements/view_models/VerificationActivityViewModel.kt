package com.andalus.lifeachievements.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.andalus.lifeachievements.VerifyMutation
import com.andalus.lifeachievements.models.Response
import com.andalus.lifeachievements.networking.MutationRequest

class VerificationActivityViewModel : ViewModel() {

    private val verificationMutation = MutableLiveData<VerifyMutation>()
    val response: LiveData<Response<VerifyMutation.Data>> =
        Transformations.switchMap(verificationMutation) {
            MutationRequest<VerifyMutation.Data, VerifyMutation.Variables, VerifyMutation>().sendRequest(
                it
            )
        }

    fun verify(email: String, code: String) {
        verificationMutation.value = VerifyMutation(email, code)
    }
}