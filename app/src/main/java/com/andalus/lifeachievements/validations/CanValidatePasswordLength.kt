package com.andalus.lifeachievements.validations

import com.google.android.material.textfield.TextInputEditText

interface CanValidatePasswordLength {
    fun validatePasswordLength(textInputEditText: TextInputEditText): Boolean
}