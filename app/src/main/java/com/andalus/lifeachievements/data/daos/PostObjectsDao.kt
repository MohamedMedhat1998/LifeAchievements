package com.andalus.lifeachievements.data.daos

import com.andalus.lifeachievements.data.PostsDatabase
import com.andalus.lifeachievements.models.Achievement
import com.andalus.lifeachievements.models.PostObject
import com.andalus.lifeachievements.models.User
import com.andalus.lifeachievements.utils.DatabaseConstants
import com.andalus.lifeachievements.utils.DatabaseConstants.AchievementTable.*
import com.andalus.lifeachievements.utils.DatabaseConstants.PostTable.*
import com.andalus.lifeachievements.utils.DatabaseConstants.UserTable.*

class PostObjectsDao(private val postsDatabase: PostsDatabase) {

    companion object {
        private var instance: PostObjectsDao? = null
        fun getInstance(postsDatabase: PostsDatabase): PostObjectsDao {
            if (instance == null) {
                instance = PostObjectsDao(postsDatabase)
            }
            return instance!!
        }
    }

    suspend fun getPostObjects(): List<PostObject> {
        val cursor =
            postsDatabase.openHelper.readableDatabase.query("SELECT * FROM Post,User,Achievement WHERE Post.ownerId = User.id AND Post.achievementId = Achievement.id")

        var postObject: PostObject
        var id: String
        var date: String

        var user: User
        var ownerId: String
        var firstName: String
        var lastName: String
        var email: String
        var phone: String
        var username: String
        var gender: String
        var picture: String
        var country: String
        var password: String

        var achievement: Achievement
        var achievementId: String
        var title: String
        var description: String
        var days: Int
        var type: String
        var published: Boolean

        val result = mutableListOf<PostObject>()

        while (cursor.moveToNext()) {
            ownerId = cursor.getString(cursor.getColumnIndex(COLUMN_OWNER_ID))
            firstName = cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME))
            lastName = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME))
            email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL))
            phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE))
            username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME))
            gender = cursor.getString(cursor.getColumnIndex(COLUMN_GENDER))
            picture = cursor.getString(cursor.getColumnIndex(COLUMN_PICTURE))
            country = cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY))
            password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD))
            user = User(
                ownerId,
                firstName,
                lastName,
                email,
                phone,
                username,
                gender,
                picture,
                country,
                password
            )

            achievementId = cursor.getString(cursor.getColumnIndex(COLUMN_ACHIEVEMENT_ID))
            title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
            description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
            days = cursor.getInt(cursor.getColumnIndex(COLUMN_DAYS))
            type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE))
            published = cursor.getInt(cursor.getColumnIndex(COLUMN_PUBLISHED)) > 1
            achievement = Achievement(achievementId, title, description, days, type, published)

            id = cursor.getString(cursor.getColumnIndex(DatabaseConstants.PostTable.COLUMN_ID))
            date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))
            postObject = PostObject(id, user, date, achievement)
            result.add(postObject)
        }
        return result
    }
}