package com.andalus.lifeachievements.behaviors

import com.google.android.material.textfield.TextInputEditText

interface CanValidatePasswordLength {
    fun validatePasswordLength(textInputEditText: TextInputEditText): Boolean
}