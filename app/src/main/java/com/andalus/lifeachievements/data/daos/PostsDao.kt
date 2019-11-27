package com.andalus.lifeachievements.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.andalus.lifeachievements.models.Post

@Dao
interface PostsDao {

    @Query("SELECT * FROM Post")
    suspend fun getAllPosts(): List<Post>

    @Insert
    suspend fun insertPosts(vararg posts: Post)

}