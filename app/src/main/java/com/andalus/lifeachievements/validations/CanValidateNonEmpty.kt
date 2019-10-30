package com.andalus.lifeachievements.validations

import com.google.android.material.textfield.TextInputEditText

interface CanValidateNonEmpty {

    fun validateNonEmpty(textInputEditText: TextInputEditText): Boolean
}