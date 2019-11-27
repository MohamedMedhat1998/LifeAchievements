package com.andalus.lifeachievements.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andalus.lifeachievements.utils.DatabaseConstants.UserTable.*

@Entity
data class User(
    @PrimaryKey @ColumnInfo(name = COLUMN_ID) val id: String,
    @ColumnInfo(name = COLUMN_FIRST_NAME) val firstName: String,
    @ColumnInfo(name = COLUMN_LAST_NAME) val lastName: String,
    @ColumnInfo(name = COLUMN_EMAIL) val email: String,
    @ColumnInfo(name = COLUMN_PHONE) val phone: String,
    @ColumnInfo(name = COLUMN_USERNAME) val username: String,
    @ColumnInfo(name = COLUMN_GENDER) val gender: String,
    @ColumnInfo(name = COLUMN_PICTURE) val picture: String,
    @ColumnInfo(name = COLUMN_COUNTRY) val country: String,
    @ColumnInfo(name = COLUMN_PASSWORD) val password: String
)

/*
picture
email
phone
*/
