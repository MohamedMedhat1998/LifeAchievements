package com.andalus.lifeachievements.data.daos

import androidx.room.Dao
import androidx.room.Insert
import com.andalus.lifeachievements.models.Achievement

@Dao
interface AchievementsDao {

    @Insert
    suspend fun insertAchievements(vararg achievements: Achievement)

}