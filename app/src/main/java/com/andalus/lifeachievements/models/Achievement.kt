package com.andalus.lifeachievements.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andalus.lifeachievements.utils.DatabaseConstants.AchievementTable.*

@Entity
data class Achievement(
    /*val id: String,
    val owner: User,
    val title: String,
    val description: String,
    val badge: String,
    val state: String*/
    @PrimaryKey @ColumnInfo(name = ID) val id: String,
    @ColumnInfo(name = TITLE) val title: String,
    @ColumnInfo(name = DESCRIPTION) val description: String,
    @ColumnInfo(name = DAYS) val days: Int,
    @ColumnInfo(name = TYPE) val type: String,
    @ColumnInfo(name = PUBLISHED) val published: Boolean
)