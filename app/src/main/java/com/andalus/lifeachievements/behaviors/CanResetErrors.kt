package com.andalus.lifeachievements.behaviors

import com.google.android.material.textfield.TextInputEditText

interface CanResetErrors {
    fun resetErrors(vararg textInputEditText: TextInputEditText)
}