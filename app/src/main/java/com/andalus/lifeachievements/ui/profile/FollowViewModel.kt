package com.andalus.lifeachievements.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.andalus.lifeachievements.MakeFriendMutation
import com.andalus.lifeachievements.models.Response
import com.andalus.lifeachievements.networking.MutationRequest
import com.andalus.lifeachievements.repositories.TokenRepository
import com.andalus.lifeachievements.view_models.TokenViewModel

class FollowViewModel(tokenRepository: TokenRepository) : TokenViewModel(tokenRepository) {

    private lateinit var id: String

    private val makeFriendMutation = MutableLiveData<MakeFriendMutation>()
    val response: LiveData<Response<MakeFriendMutation.Data>> =
        Transformations.switchMap(makeFriendMutation) {
            MutationRequest<MakeFriendMutation.Data, MakeFriendMutation.Variables, MakeFriendMutation>(
                tokenRepository
            ).sendRequest(it)
        }

    fun follow(id: String) {
        this.id = id
        makeFriendMutation.value = MakeFriendMutation.builder().id(id).build()
    }

    override fun refreshWithNewToken(token: String) {
        tokenRepository.setToken(token)
        follow(id)
    }
}