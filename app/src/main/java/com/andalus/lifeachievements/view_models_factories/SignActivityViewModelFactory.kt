package com.andalus.lifeachievements.view_models_factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andalus.lifeachievements.repositories.TokenRepository
import com.andalus.lifeachievements.view_models.SignActivityViewModel

class SignActivityViewModelFactory(private val tokenRepository: TokenRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SignActivityViewModel(tokenRepository) as T
    }

}