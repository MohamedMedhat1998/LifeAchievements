package com.andalus.lifeachievements.ui.profile

import androidx.lifecycle.*
import com.andalus.lifeachievements.GetUserQuery
import com.andalus.lifeachievements.enums.State
import com.andalus.lifeachievements.models.Response
import com.andalus.lifeachievements.models.User
import com.andalus.lifeachievements.networking.QueryRequest
import com.andalus.lifeachievements.repositories.TokenRepository
import com.andalus.lifeachievements.view_models.TokenViewModel

class ProfileViewModel(tokenRepository: TokenRepository, private val id: String) :
    TokenViewModel(tokenRepository) {

    private val getUserQuery = MutableLiveData<GetUserQuery>()
    val response: LiveData<Response<GetUserQuery.Data>> = Transformations.switchMap(getUserQuery) {
        QueryRequest<GetUserQuery.Data, GetUserQuery.Variables, GetUserQuery>(tokenRepository).sendRequest(
            it
        )
    }

    val user = MediatorLiveData<User>()
    val state = MutableLiveData<State>()

    init {
        state.value = State.LoadingState
        getUserQuery.value = GetUserQuery.builder().id(id).build()
        user.addSource(response) {
            val tempUser: User
            if (it.errors.isEmpty()) {
                state.value = State.SuccessState
                val data = it.data?.user
                if (data != null) {
                    tempUser = User(
                        data.id(),
                        data.first_name(),
                        data.last_name(),
                        "",
                        "",
                        data.username(),
                        data.gender().toString(),
                        data.picture(),
                        "",
                        ""
                    )
                    user.value = tempUser
                }
            } else {
                state.value = State.ErrorState
            }
        }
    }

    override fun refreshWithNewToken(token : String){
        state.value = State.LoadingState
        tokenRepository.setToken(token)
        getUserQuery.value = GetUserQuery.builder().id(id).build()
    }

}