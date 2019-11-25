package com.andalus.lifeachievements.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andalus.lifeachievements.utils.DatabaseConstants.UserTable.*

@Entity
data class User(
    @PrimaryKey @ColumnInfo(name = ID) val id: String,
    @ColumnInfo(name = FIRST_NAME) val firstName: String,
    @ColumnInfo(name = LAST_NAME) val lastName: String,
    @ColumnInfo(name = EMAIL) val email: String,
    @ColumnInfo(name = PHONE) val phone: String,
    @ColumnInfo(name = USERNAME) val username: String,
    @ColumnInfo(name = GENDER) val gender: String,
    @ColumnInfo(name = PICTURE) val picture: String,
    @ColumnInfo(name = COUNTRY) val country: String,
    @ColumnInfo(name = PASSWORD) val password: String
)

/*
picture
email
phone
*/
