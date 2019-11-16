package com.andalus.lifeachievements.models

data class Achievement(
    val id: String,
    val owner: User,
    val title: String,
    val description: String,
    val badge: String,
    val state: String
)