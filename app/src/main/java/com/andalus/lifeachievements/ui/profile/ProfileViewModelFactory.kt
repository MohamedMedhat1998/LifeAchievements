package com.andalus.lifeachievements.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andalus.lifeachievements.repositories.TokenRepository

class ProfileViewModelFactory(val tokenRepository: TokenRepository, val id: String) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(
            tokenRepository,
            id
        ) as T
    }

}