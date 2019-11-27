package com.andalus.lifeachievements.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.andalus.lifeachievements.utils.DatabaseConstants
import com.andalus.lifeachievements.utils.DatabaseConstants.PostTable.*


@Entity(
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = [DatabaseConstants.UserTable.COLUMN_ID],
        childColumns = [COLUMN_OWNER_ID]
    ),
        ForeignKey(
            entity = Achievement::class,
            parentColumns = [DatabaseConstants.AchievementTable.COLUMN_ID],
            childColumns = [COLUMN_ACHIEVEMENT_ID]
        )]
)
data class Post(
    @PrimaryKey @ColumnInfo(name = COLUMN_ID) val id: String,
    @ColumnInfo(name = COLUMN_OWNER_ID) val ownerId: String,
    @ColumnInfo(name = COLUMN_DATE) val date: String,
    @ColumnInfo(name = COLUMN_ACHIEVEMENT_ID) val achievementId: String
    /*@ColumnInfo(name = Constants.COLUMN_LIKES) val likes: MutableList<User>,
    @ColumnInfo(name = Constants.COLUMN_COMMENTS) val comments: MutableList<Comment>,
    @ColumnInfo(name = Constants.COLUMN_SHARES) val shares: MutableList<User>*/
)