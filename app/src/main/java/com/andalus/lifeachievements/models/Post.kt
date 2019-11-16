package com.andalus.lifeachievements.models

data class Post(
    val id: String,
    val owner: User,
    val date:String,
    val achievement: Achievement,
    val likes: MutableList<User>,
    val comments: MutableList<Comment>,
    val shares: MutableList<User>
)