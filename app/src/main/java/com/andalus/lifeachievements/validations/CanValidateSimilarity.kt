package com.andalus.lifeachievements.validations

import com.google.android.material.textfield.TextInputEditText

interface CanValidateSimilarity {
    fun validateSimilarity(
        firstTextInputEditText: TextInputEditText,
        secondTextInputEditText: TextInputEditText
    ): Boolean
}