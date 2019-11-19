package com.andalus.lifeachievements.models

import com.andalus.lifeachievements.type.Type

data class Achievement(
    /*val id: String,
    val owner: User,
    val title: String,
    val description: String,
    val badge: String,
    val state: String*/
    val title: String,
    val description: String,
    val days: Int,
    val type: Type,
    val published: Boolean
)