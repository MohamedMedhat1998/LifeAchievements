package com.andalus.lifeachievements.behaviors

import android.widget.EditText

interface CanValidatePhoneNumber {

    fun validatePhoneNumber(editText: EditText): Boolean

}