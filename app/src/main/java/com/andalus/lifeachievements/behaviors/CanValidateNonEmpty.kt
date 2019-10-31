package com.andalus.lifeachievements.behaviors

import android.widget.EditText

interface CanValidateNonEmpty {

    fun validateNonEmpty(editText: EditText): Boolean
}