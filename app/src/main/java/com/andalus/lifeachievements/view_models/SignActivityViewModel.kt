package com.andalus.lifeachievements.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignActivityViewModel : ViewModel() {

    val completeSignedUp = MutableLiveData<Boolean>()
    val emailAddress = MutableLiveData<String>()
    val newSignUp = MutableLiveData<Boolean>()

    fun setSignedUp(signed: Boolean, email: String = "") {
        completeSignedUp.value = signed
        emailAddress.value = email
    }

    fun setNewSignUp(signed: Boolean) {
        newSignUp.value = signed
    }

}