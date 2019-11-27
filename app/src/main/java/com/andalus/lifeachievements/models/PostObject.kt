package com.andalus.lifeachievements.models

class PostObject(
    val id: String,
    val owner: User,
    val date: String,
    val achievement: Achievement
    /*@ColumnInfo(name = Constants.COLUMN_LIKES) val likes: MutableList<User>,
    @ColumnInfo(name = Constants.COLUMN_COMMENTS) val comments: MutableList<Comment>,
    @ColumnInfo(name = Constants.COLUMN_SHARES) val shares: MutableList<User>*/
)