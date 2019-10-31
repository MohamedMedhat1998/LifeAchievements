package com.andalus.lifeachievements.behaviors

import com.google.android.material.textfield.TextInputEditText

interface CanValidatePhoneNumber {

    fun validatePhoneNumber(textInputEditText: TextInputEditText): Boolean

}