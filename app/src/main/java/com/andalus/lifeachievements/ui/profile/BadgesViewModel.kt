package com.andalus.lifeachievements.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.andalus.lifeachievements.UserAchievementsQuery
import com.andalus.lifeachievements.enums.State
import com.andalus.lifeachievements.models.Badge
import com.andalus.lifeachievements.models.Response
import com.andalus.lifeachievements.networking.QueryRequest
import com.andalus.lifeachievements.repositories.TokenRepository
import com.andalus.lifeachievements.view_models.TokenViewModel

class BadgesViewModel(tokenRepository: TokenRepository) : TokenViewModel(tokenRepository) {

    private val achievementsQuery = MutableLiveData<UserAchievementsQuery>()
    val response: LiveData<Response<UserAchievementsQuery.Data>> =
        Transformations.switchMap(achievementsQuery) {
            QueryRequest<UserAchievementsQuery.Data, UserAchievementsQuery.Variables, UserAchievementsQuery>(
                tokenRepository
            ).sendRequest(
                it
            )
        }

    val badgesList = MediatorLiveData<List<Badge>>()
    val state = MediatorLiveData<State>()

    init {
        state.value = State.LoadingState
        achievementsQuery.value = UserAchievementsQuery.builder().active(true).build()

        badgesList.addSource(response) {
            if (it.errors.isEmpty()) {
                state.value = State.SuccessState
                val list = mutableListOf<Badge>()
                badgesList.value = list
                var badge: Badge
                it.data?.userAchievements()?.forEach { achievement ->
                    badge = Badge(achievement.title(), achievement.picture())
                    list.add(badge)
                }
            } else {
                state.value = State.ErrorState
            }
        }
    }

    override fun refreshWithNewToken(token: String) {
        state.value = State.LoadingState
        tokenRepository.setToken(token)
        achievementsQuery.value = UserAchievementsQuery.builder().active(true).build()
    }

}