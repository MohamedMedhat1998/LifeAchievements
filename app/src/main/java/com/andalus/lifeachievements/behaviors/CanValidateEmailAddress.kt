package com.andalus.lifeachievements.behaviors

import com.google.android.material.textfield.TextInputEditText

interface CanValidateEmailAddress {

    fun validateEmailAddress(textInputEditText: TextInputEditText): Boolean

}