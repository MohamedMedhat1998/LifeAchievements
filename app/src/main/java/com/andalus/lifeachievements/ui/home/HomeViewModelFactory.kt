package com.andalus.lifeachievements.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andalus.lifeachievements.repositories.LoggedUserRepository

class HomeViewModelFactory(private val userRepository: LoggedUserRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(userRepository) as T
    }
}