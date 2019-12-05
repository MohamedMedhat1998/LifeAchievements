package com.andalus.lifeachievements.view_models_factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andalus.lifeachievements.repositories.TokenRepository
import com.andalus.lifeachievements.view_models.FollowViewModel

class FollowViewModelFacory(val tokenRepository: TokenRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FollowViewModel(tokenRepository) as T
    }

}