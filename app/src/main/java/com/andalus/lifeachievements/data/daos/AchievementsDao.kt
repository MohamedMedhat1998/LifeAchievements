package com.andalus.lifeachievements.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.andalus.lifeachievements.models.Achievement

@Dao
interface AchievementsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAchievements(vararg achievements: Achievement)

}