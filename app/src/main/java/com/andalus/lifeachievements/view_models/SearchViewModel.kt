package com.andalus.lifeachievements.view_models

import androidx.lifecycle.*
import com.andalus.lifeachievements.SearchUsersQuery
import com.andalus.lifeachievements.enums.State
import com.andalus.lifeachievements.models.MiniUser
import com.andalus.lifeachievements.models.Response
import com.andalus.lifeachievements.networking.QueryRequest
import com.andalus.lifeachievements.repositories.TokenRepository

class SearchViewModel(private val tokenRepository: TokenRepository) : ViewModel() {

    private lateinit var searchKeyword: String

    private val searchUsersQuery = MutableLiveData<SearchUsersQuery>()
    val response: LiveData<Response<SearchUsersQuery.Data>> =
        Transformations.switchMap(searchUsersQuery) {
            QueryRequest<SearchUsersQuery.Data, SearchUsersQuery.Variables, SearchUsersQuery>(
                tokenRepository
            ).sendRequest(it)
        }

    val usersList = MediatorLiveData<List<MiniUser>>()
    val state = MutableLiveData<State>()

    init {
        state.value = State.NormalState
        usersList.addSource(response) {
            if (it.errors.isEmpty()) {
                if (it.data != null) {
                    state.value = State.SuccessState
                    val users = mutableListOf<MiniUser>()
                    it.data.searchUsers().forEach { user ->
                        users.add(
                            MiniUser(
                                user.id(),
                                "${user.first_name()} ${user.last_name()}",
                                user.username(),
                                user.picture()
                            )
                        )
                    }
                    usersList.value = users
                }
            } else {
                state.value = State.ErrorState
            }
        }
    }

    fun refreshWithNewToken(token: String) {
        state.value = State.LoadingState
        tokenRepository.setToken(token)
        search(searchKeyword)
    }

    fun resetToken(){
        tokenRepository.setToken("")
    }

    fun search(keyword: String) {
        this.searchKeyword = keyword
        state.value = State.LoadingState
        searchUsersQuery.value = SearchUsersQuery.builder().name(keyword).build()
    }
}