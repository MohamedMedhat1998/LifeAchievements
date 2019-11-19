package com.andalus.lifeachievements.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.andalus.lifeachievements.CreateChallengeMutation
import com.andalus.lifeachievements.data.TokenRepository
import com.andalus.lifeachievements.models.Achievement
import com.andalus.lifeachievements.models.Response
import com.andalus.lifeachievements.networking.MutationRequest

class CreateChallengeViewModel(private val tokenRepository: TokenRepository) : ViewModel() {

    private val createAchievementMutation = MutableLiveData<CreateChallengeMutation>()
    val response: LiveData<Response<CreateChallengeMutation.Data>> =
        Transformations.switchMap(createAchievementMutation) {
            MutationRequest<CreateChallengeMutation.Data, CreateChallengeMutation.Variables, CreateChallengeMutation>(
                tokenRepository
            ).sendRequest(it)
        }

    fun createAchievement(achievement: Achievement) {
        createAchievementMutation.value =
            CreateChallengeMutation.builder().title(achievement.title)
                .description(achievement.description).days(achievement.days)
                .published(achievement.published).type(achievement.type)
                .build()
    }
}