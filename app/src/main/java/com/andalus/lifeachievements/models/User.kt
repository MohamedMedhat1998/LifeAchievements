package com.andalus.lifeachievements.models

import com.andalus.lifeachievements.type.Gender

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val username: String,
    val gender: Gender,
    val picture: String,
    val country: String,
    val password: String
)

/*
picture
email
phone
*/
