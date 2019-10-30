package com.andalus.lifeachievements.validations

import com.google.android.material.textfield.TextInputEditText

interface CanValidatePhoneNumber {

    fun validatePhoneNumber(textInputEditText: TextInputEditText): Boolean

}