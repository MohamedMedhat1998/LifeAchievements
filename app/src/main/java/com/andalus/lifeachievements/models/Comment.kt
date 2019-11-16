package com.andalus.lifeachievements.models

data class Comment(val id: String, val owner: User, val body: String, val date: String)