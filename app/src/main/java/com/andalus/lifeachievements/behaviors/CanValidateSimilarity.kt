package com.andalus.lifeachievements.behaviors

import com.google.android.material.textfield.TextInputEditText

interface CanValidateSimilarity {
    fun validateSimilarity(
        firstTextInputEditText: TextInputEditText,
        secondTextInputEditText: TextInputEditText
    ): Boolean
}