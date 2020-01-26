package com.andalus.lifeachievements.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andalus.lifeachievements.models.MiniUser
import com.andalus.lifeachievements.repositories.LoggedUserRepository

class HomeViewModel(private val userRepository: LoggedUserRepository) : ViewModel() {

    val miniUser = MutableLiveData<MiniUser>()

    init {
        miniUser.value = userRepository.getUser()
    }
}