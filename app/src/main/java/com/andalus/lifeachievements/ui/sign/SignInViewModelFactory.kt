package com.andalus.lifeachievements.ui.sign

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andalus.lifeachievements.repositories.LoggedUserRepository
import com.andalus.lifeachievements.repositories.TokenRepository

class SignInViewModelFactory(
    private val tokenRepository: TokenRepository,
    private val userRepository: LoggedUserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SignInViewModel(
            tokenRepository,
            userRepository
        ) as T
    }
}