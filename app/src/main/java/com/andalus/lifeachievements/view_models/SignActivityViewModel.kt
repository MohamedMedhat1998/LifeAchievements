package com.andalus.lifeachievements.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andalus.lifeachievements.repositories.TokenRepository

class SignActivityViewModel(tokenRepository: TokenRepository) : ViewModel() {

    val completeSignedUp = MutableLiveData<Boolean>()
    val emailAddress = MutableLiveData<String>()
    val newSignUp = MutableLiveData<Boolean>()
    val token = tokenRepository.getToken()

    fun setSignedUp(signed: Boolean, email: String = "") {
        completeSignedUp.value = signed
        emailAddress.value = email
    }

    fun setNewSignUp(signed: Boolean) {
        newSignUp.value = signed
    }

    fun setEmailAddress(email: String) {
        emailAddress.value = email
    }

}