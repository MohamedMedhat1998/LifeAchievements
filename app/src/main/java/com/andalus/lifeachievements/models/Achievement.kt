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
    @PrimaryKey @ColumnInfo(name = COLUMN_ID) val id: String,
    @ColumnInfo(name = COLUMN_TITLE) val title: String,
    @ColumnInfo(name = COLUMN_DESCRIPTION) val description: String,
    @ColumnInfo(name = COLUMN_DAYS) val days: Int,
    @ColumnInfo(name = COLUMN_TYPE) val type: String,
    @ColumnInfo(name = COLUMN_PUBLISHED) val published: Boolean
)