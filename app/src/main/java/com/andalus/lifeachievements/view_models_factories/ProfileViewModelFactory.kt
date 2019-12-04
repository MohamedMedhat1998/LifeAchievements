package com.andalus.lifeachievements.view_models_factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andalus.lifeachievements.repositories.TokenRepository
import com.andalus.lifeachievements.view_models.ProfileViewModel

class ProfileViewModelFactory(val tokenRepository: TokenRepository, val id: String) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(tokenRepository,id) as T
    }

}