package com.andalus.lifeachievements.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andalus.lifeachievements.repositories.TokenRepository

@Suppress("UNCHECKED_CAST")
class BadgesViewModelFactory(val tokenRepository: TokenRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BadgesViewModel(tokenRepository) as T
    }

}