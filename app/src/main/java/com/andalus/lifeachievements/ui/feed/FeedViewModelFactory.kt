package com.andalus.lifeachievements.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andalus.lifeachievements.data.PostsDatabase
import com.andalus.lifeachievements.repositories.TokenRepository
import com.andalus.lifeachievements.utils.NetworkStateTracer

class FeedViewModelFactory(
    private val tokenRepository: TokenRepository,
    private val networkStateTracer: NetworkStateTracer,
    private val database: PostsDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FeedViewModel(
            tokenRepository,
            networkStateTracer,
            database
        ) as T
    }

}