package com.andalus.lifeachievements.validations

import com.google.android.material.textfield.TextInputEditText

interface CanValidateEmailAddress {

    fun validateEmailAddress(textInputEditText: TextInputEditText): Boolean

}