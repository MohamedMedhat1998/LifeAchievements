package com.andalus.lifeachievements.behaviors

import android.widget.EditText

interface CanResetErrors {
    fun resetErrors(vararg editText: EditText)
}