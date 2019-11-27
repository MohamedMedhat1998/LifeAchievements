package com.andalus.lifeachievements.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.andalus.lifeachievements.models.Post

@Dao
interface PostsDao {

    @Query("SELECT * FROM Post")
    suspend fun getAllPosts(): List<Post>

    @Query("SELECT * FROM Post,User,Achievement WHERE Post.ownerId = User.id AND Post.achievementId = Achievement.id")
    suspend fun getAllPostsWithJoin() : List<Post>

    @Insert
    suspend fun saveList(vararg posts: Post)

}