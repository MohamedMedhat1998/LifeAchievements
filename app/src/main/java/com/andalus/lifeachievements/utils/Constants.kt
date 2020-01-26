package com.andalus.lifeachievements.utils

import okio.JvmField

class Constants {

    companion object {
        //Networking
        const val SERVER_URL = "https://protected-basin-72892.herokuapp.com/"
        const val TOKEN_HEADER = "Authorization"
        const val TOKEN_BEARER = "Bearer"
        //Keys
        const val ERROR_MESSAGE_KEY = "msg"
        const val ERROR_FIELD_KEY = "field"
        const val EMAIL_KEY = "email-key"
        const val MINI_USER_ID_KEY = "mini-user-id"
        //Error-Types
        const val ERROR_FAILURE = "failure"
        //Response-values
        const val ERROR_USERNAME = "username"
        const val ERROR_PASSWORD = "password"
        const val ERROR_PHONE = "phone"
        const val ERROR_EMAIL = "email"
        const val ERROR_UNVERIFIED = "message"
        const val ERROR_REGISTRATION_CODE = "register_code"
        const val ERROR_INVALID_TOKEN = "NEW_TOKEN"
        const val ERROR_INVALID_USER = "MESSAGE"
        const val ERROR_TITLE = "title"
        const val ERROR_DESCRIPTION = "description"
        //Internal-errors
        const val ERROR_INVALID_ACTIVITY = "Invalid Activity"
        //Login
        const val INVALID_PHONE_NUMBER = "Invalid phone number"
        const val INVALID_EMAIL_ADDRESS = "Invalid email address"
        //Messages
        const val PASSWORD_LENGTH_MESSAGE = "Password length cannot be less than 6 characters"
        const val PASSWORD_NO_MATCH_MESSAGE = "Password didn't match"
        const val REQUIRED_MESSAGE = "This field is required"
        //preferences
        const val TOKEN_PREFERENCES_FILE_NAME = "token-cache"
        const val TOKEN_KEY = "token"
        const val USER_PREFERENCES_FILE_NAME = "user-cache"
        const val PICTURE_KEY = "picture"
        const val ID_KEY = "id"
        const val NAME_KEY = "name"
        const val USERNAME_KEY = "username"
    }

}