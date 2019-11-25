package com.andalus.lifeachievements.view_models

import androidx.lifecycle.*
import com.andalus.lifeachievements.FeedQuery
import com.andalus.lifeachievements.data.PostsDatabase
import com.andalus.lifeachievements.models.Achievement
import com.andalus.lifeachievements.models.Post
import com.andalus.lifeachievements.models.Response
import com.andalus.lifeachievements.models.User
import com.andalus.lifeachievements.networking.QueryRequest
import com.andalus.lifeachievements.repositories.TokenRepository
import com.andalus.lifeachievements.utils.NetworkStateTracer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val INITIAL_SKIP = 0
private const val INITIAL_LIMIT = 10

class FeedViewModel(
    private val tokenRepository: TokenRepository,
    private val networkStateTracer: NetworkStateTracer,
    private val database: PostsDatabase
) : ViewModel() {

    private var feedQuery = MutableLiveData<FeedQuery>()
    public val response: LiveData<Response<FeedQuery.Data>> = Transformations.switchMap(feedQuery) {
        QueryRequest<FeedQuery.Data, FeedQuery.Variables, FeedQuery>(tokenRepository).sendRequest(it)
    }

    val posts = MediatorLiveData<List<Post>>()

    init {
        feedQuery.value = FeedQuery.builder().skip(INITIAL_SKIP).limit(INITIAL_LIMIT).build()
        posts.addSource(response) {
            if (it.errors.isEmpty()) {
//                if (it.data != null) {
//                    val list = mutableListOf<Post>()
//                    var tempUser: User
//                    var tempAchievement: Achievement
//                    it.data.feed().forEach { data ->
//                        tempUser = User(
//                            data.author().id(),
//                            data.author().first_name(),
//                            data.author().last_name(),
//                            "",
//                            "",
//                            data.author().username(),
//                            data.author().gender(),
//                            data.author().picture(),
//                            "",
//                            ""
//                        )
//                        tempAchievement = Achievement(
//                            data.title(),
//                            data.description(),
//                            data.days().size,
//                            data.type(),
//                            data.published()
//                        )
//                        list.add(
//                            Post(
//                                data.id(),
//                                tempUser,
//                                data.created_at(),
//                                tempAchievement,
//                                mutableListOf(),
//                                mutableListOf(),
//                                mutableListOf()
//                            )
//                        )
//                    }
//                    posts.value = list
//                    CoroutineScope(Dispatchers.IO).launch {
//                        list.forEach { post ->
//                            database.postsDao().saveList(post)
//                        }
//                    }
//                }
            }
        }
    }

    fun loadData(skip: Int, limit: Int) {
        if (networkStateTracer.isConnected) {
            feedQuery.value = FeedQuery.builder().skip(skip).limit(limit).build()
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                posts.value = database.postsDao().getAllPosts()
            }
        }

    }
}