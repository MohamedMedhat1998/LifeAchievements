package com.andalus.lifeachievements.behaviors

import android.widget.EditText

interface CanValidateSimilarity {
    fun validateSimilarity(
        firstEditText: EditText,
        secondEditText: EditText
    ): Boolean
}