package com.andalus.lifeachievements.view_models

import androidx.lifecycle.*
import com.andalus.lifeachievements.CreateChallengeMutation
import com.andalus.lifeachievements.enums.State
import com.andalus.lifeachievements.models.Achievement
import com.andalus.lifeachievements.models.Response
import com.andalus.lifeachievements.networking.MutationRequest
import com.andalus.lifeachievements.repositories.TokenRepository
import com.andalus.lifeachievements.utils.TypeConverter

class CreateChallengeViewModel(private val tokenRepository: TokenRepository) : ViewModel() {

    private lateinit var achievement: Achievement

    private val createAchievementMutation = MutableLiveData<CreateChallengeMutation>()
    val response: LiveData<Response<CreateChallengeMutation.Data>> =
        Transformations.switchMap(createAchievementMutation) {
            MutationRequest<CreateChallengeMutation.Data, CreateChallengeMutation.Variables, CreateChallengeMutation>(
                tokenRepository
            ).sendRequest(it)
        }

    val state = MediatorLiveData<State>()

    init {
        state.value = State.NormalState
        state.addSource(response) {
            if (it != null) {
                if (it.errors.isNotEmpty()) {
                    state.value = State.ErrorState
                } else {
                    state.value = State.SuccessState
                }
            }
        }
    }

    fun createAchievement(achievement: Achievement) {
        this.achievement = achievement
        state.value = State.LoadingState
        createAchievementMutation.value =
            CreateChallengeMutation.builder().title(achievement.title)
                .description(achievement.description).days(achievement.days)
                .published(achievement.published).type(TypeConverter.getType(achievement.type))
                .build()
    }

    fun refreshWithNewToken(token: String) {
        state.value = State.LoadingState
        tokenRepository.setToken(token)
        createAchievement(achievement)
    }

    fun resetToken() {
        tokenRepository.setToken("")
    }
}