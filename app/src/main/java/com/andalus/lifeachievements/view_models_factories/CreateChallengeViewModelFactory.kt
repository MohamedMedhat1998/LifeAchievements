package com.andalus.lifeachievements.view_models_factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andalus.lifeachievements.data.TokenRepository
import com.andalus.lifeachievements.view_models.CreateChallengeViewModel

class CreateChallengeViewModelFactory(private val tokenRepository: TokenRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CreateChallengeViewModel(tokenRepository) as T
    }
}