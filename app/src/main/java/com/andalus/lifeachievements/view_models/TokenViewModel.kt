package com.andalus.lifeachievements.view_models

import androidx.lifecycle.ViewModel
import com.andalus.lifeachievements.repositories.TokenRepository

abstract class TokenViewModel(val tokenRepository: TokenRepository) : ViewModel() {

    abstract fun refreshWithNewToken(token: String)

    fun resetToken() {
        tokenRepository.setToken("")
    }

}