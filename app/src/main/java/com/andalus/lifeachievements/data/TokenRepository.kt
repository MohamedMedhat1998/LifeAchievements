package com.andalus.lifeachievements.data

import android.content.Context
import com.andalus.lifeachievements.utils.Constants

class TokenRepository(val context: Context) {

    fun getToken(): String {
        return context.getSharedPreferences(
            Constants.PREFERENCES_FILE_NAME,
            Context.MODE_PRIVATE
        ).getString(Constants.TOKEN_KEY, "")!!
    }

    fun setToken(token: String) {
        val editor =
            context.getSharedPreferences(Constants.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
                .edit()
        editor.putString(Constants.TOKEN_KEY, token)
        editor.apply()
    }
}