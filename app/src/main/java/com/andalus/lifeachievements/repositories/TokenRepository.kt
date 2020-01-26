package com.andalus.lifeachievements.repositories

import android.content.Context
import com.andalus.lifeachievements.utils.Constants

class TokenRepository(val context: Context) {

    fun getToken(): String {
        return context.getSharedPreferences(
            Constants.TOKEN_PREFERENCES_FILE_NAME,
            Context.MODE_PRIVATE
        ).getString(Constants.TOKEN_KEY, "")!!
    }

    fun setToken(token: String) {
        val editor =
            context.getSharedPreferences(Constants.TOKEN_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
                .edit()
        editor.putString(Constants.TOKEN_KEY, token)
        editor.apply()
    }
}