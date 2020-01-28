package com.andalus.lifeachievements.ui.create_challenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andalus.lifeachievements.repositories.TokenRepository

class CreateChallengeViewModelFactory(private val tokenRepository: TokenRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CreateChallengeViewModel(
            tokenRepository
        ) as T
    }
}