package com.andalus.lifeachievements.repositories

import android.content.Context
import com.andalus.lifeachievements.LoginMutation
import com.andalus.lifeachievements.models.MiniUser
import com.andalus.lifeachievements.utils.Constants

class LoggedUserRepository(private val context: Context) {

    fun setUser(user: LoginMutation.LoginUser) {
        val prefs =
            context.getSharedPreferences(Constants.USER_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
                .edit()
        prefs.putString(Constants.ID_KEY, user.id())
        prefs.putString(Constants.PICTURE_KEY, user.picture())
        prefs.putString(Constants.NAME_KEY, user.first_name() + " " + user.last_name())
        prefs.putString(Constants.USERNAME_KEY, user.username())
        prefs.apply()
    }

    fun getUser(): MiniUser {
        val prefs =
            context.getSharedPreferences(Constants.USER_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
        val id = prefs.getString(Constants.ID_KEY, "")
        val name = prefs.getString(Constants.NAME_KEY, "")
        val username = prefs.getString(Constants.USERNAME_KEY, "")
        val picture = prefs.getString(Constants.PICTURE_KEY, "")
        return MiniUser(id!!, name!!, username!!, picture!!)
    }
}