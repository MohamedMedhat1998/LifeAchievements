package com.andalus.lifeachievements.ui.feed

import androidx.lifecycle.*
import com.andalus.lifeachievements.FeedQuery
import com.andalus.lifeachievements.data.PostsDatabase
import com.andalus.lifeachievements.models.*
import com.andalus.lifeachievements.networking.QueryRequest
import com.andalus.lifeachievements.repositories.TokenRepository
import com.andalus.lifeachievements.utils.NetworkStateTracer
import com.andalus.lifeachievements.view_models.TokenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val INITIAL_SKIP = 0
private const val INITIAL_LIMIT = 10

class FeedViewModel(
    tokenRepository: TokenRepository,
    private val networkStateTracer: NetworkStateTracer,
    private val database: PostsDatabase
) : TokenViewModel(tokenRepository) {

    private var feedQuery = MutableLiveData<FeedQuery>()
    public val response: LiveData<Response<FeedQuery.Data>> = Transformations.switchMap(feedQuery) {
        QueryRequest<FeedQuery.Data, FeedQuery.Variables, FeedQuery>(tokenRepository).sendRequest(it)
    }

    val posts = MediatorLiveData<List<PostObject>>()

    init {
        feedQuery.value = FeedQuery.builder().skip(INITIAL_SKIP).limit(
            INITIAL_LIMIT
        ).build()
        posts.addSource(response) {
            if (it.errors.isEmpty()) {
                if (it.data != null) {
                    var tempUser: User
                    var tempAchievement: Achievement
                    var post: Post
                    val result = mutableListOf<PostObject>()
                    it.data.feed().forEach { data ->
                        tempUser = User(
                            data.author().id(),
                            data.author().first_name(),
                            data.author().last_name(),
                            "",
                            "",
                            data.author().username(),
                            data.author().gender().toString(),
                            data.author().picture(),
                            "",
                            ""
                        )
                        tempAchievement = Achievement(
                            data.id(),
                            data.title(),
                            data.description(),
                            data.days().size,
                            data.type().toString(),
                            data.published()
                        )
                        post = Post(
                            data.id(),
                            data.author().id(),
                            data.created_at(),
                            data.id()
                        )
                        CoroutineScope(Dispatchers.IO).launch {
                            database.usersDao().insertUsers(tempUser)
                            database.achievementsDao().insertAchievements(tempAchievement)
                            database.postsDao().insertPosts(post)
                        }
                        result.add(
                            PostObject(
                                data.id(),
                                tempUser,
                                data.created_at(),
                                tempAchievement
                            )
                        )
                    }
                    posts.value = result
                }
            }
        }
    }

    fun loadData(skip: Int, limit: Int) {
        if (networkStateTracer.isConnected) {
            feedQuery.value = FeedQuery.builder().skip(skip).limit(limit).build()
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                posts.value = database.postObjectsDao().getPostObjects()
            }
        }

    }

    override fun refreshWithNewToken(token: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}