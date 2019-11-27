package com.andalus.lifeachievements.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.andalus.lifeachievements.data.daos.PostObjectsDao
import com.andalus.lifeachievements.data.daos.PostsDao
import com.andalus.lifeachievements.models.Achievement
import com.andalus.lifeachievements.models.Post
import com.andalus.lifeachievements.models.User
import com.andalus.lifeachievements.utils.DatabaseConstants

@Database(
    entities = [Post::class, User::class, Achievement::class],
    version = 1,
    exportSchema = false
)
abstract class PostsDatabase : RoomDatabase() {

    companion object {
        private var instance: PostsDatabase? = null

        fun getInstance(context: Context): PostsDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    PostsDatabase::class.java,
                    DatabaseConstants.DATABASE_NAME
                ).build()
            }
            return instance!!
        }

    }

    abstract fun postsDao(): PostsDao

    fun getPostObjectsDao(): PostObjectsDao {
        return PostObjectsDao.getInstance(this)
    }
}