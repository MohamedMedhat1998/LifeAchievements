package com.andalus.lifeachievements.ui.sign

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andalus.lifeachievements.repositories.TokenRepository

class SignActivityViewModelFactory(private val tokenRepository: TokenRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SignActivityViewModel(
            tokenRepository
        ) as T
    }

}