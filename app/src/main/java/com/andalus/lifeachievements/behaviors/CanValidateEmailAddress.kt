package com.andalus.lifeachievements.behaviors

import android.widget.EditText

interface CanValidateEmailAddress {

    fun validateEmailAddress(editText: EditText): Boolean

}