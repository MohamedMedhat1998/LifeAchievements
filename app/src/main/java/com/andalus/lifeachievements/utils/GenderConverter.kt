package com.andalus.lifeachievements.utils

import com.andalus.lifeachievements.type.Gender

class GenderConverter {
    companion object {
        @JvmStatic
        fun getGender(gender: String): Gender {
            return when (gender) {
                Gender.MALE.toString() -> Gender.MALE
                Gender.FEMALE.toString() -> Gender.FEMALE
                else -> Gender.`$UNKNOWN`
            }
        }
    }
}