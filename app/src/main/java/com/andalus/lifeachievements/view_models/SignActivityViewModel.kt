package com.andalus.lifeachievements.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class SignActivityViewModel : ViewModel() {

    val signedUp = MutableLiveData<Boolean>()


    fun setSignedUp(boolean: Boolean){
        signedUp.value = boolean
    }


}