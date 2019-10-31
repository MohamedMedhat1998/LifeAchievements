package com.andalus.lifeachievements.behaviors

import com.google.android.material.textfield.TextInputEditText

interface CanValidateNonEmpty {

    fun validateNonEmpty(textInputEditText: TextInputEditText): Boolean
}