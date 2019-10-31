package com.andalus.lifeachievements.behaviors

import android.widget.EditText

interface CanValidatePasswordLength {
    fun validatePasswordLength(editText: EditText): Boolean
}