package com.andalus.lifeachievements.data.daos

import androidx.room.Dao
import androidx.room.Insert
import com.andalus.lifeachievements.models.User

@Dao
interface UsersDao {

    @Insert
    suspend fun insertUsers(vararg users: User)

}