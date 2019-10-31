package com.andalus.lifeachievements.utils

import android.util.Patterns
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class Functions {

    companion object {
        val validatePhoneNumber: (TextInputEditText) -> Boolean = {
            if (Patterns.PHONE.matcher(it.text.toString().trim()).matches()) {
                true
            } else {
                it.error = Constants.INVALID_PHONE_NUMBER
                it.requestFocus()
                false
            }
        }

        val validateEmailAddress: (TextInputEditText) -> Boolean = {
            if (Patterns.EMAIL_ADDRESS.matcher(it.text.toString().trim()).matches()) {
                true
            } else {
                it.error = Constants.INVALID_EMAIL_ADDRESS
                it.requestFocus()
                false
            }
        }

        val validatePasswordLength: (TextInputEditText) -> Boolean = {
            if (it.text.toString().length < 6) {
                it.error = Constants.PASSWORD_LENGTH_MESSAGE
                it.requestFocus()
                false
            } else {
                true
            }
        }

        val validateSimilarity: (TextInputEditText, TextInputEditText) -> Boolean =
            { firstTextInputEditText, secondTextInputEditText ->
                if (firstTextInputEditText.text.toString() != secondTextInputEditText.text.toString()) {
                    firstTextInputEditText.error = Constants.PASSWORD_NO_MATCH_MESSAGE
                    secondTextInputEditText.error = Constants.PASSWORD_NO_MATCH_MESSAGE
                    firstTextInputEditText.requestFocus()
                    false
                } else {
                    true
                }
            }

        val validateNonEmpty: (TextInputEditText) -> Boolean = {
            if (it.text.toString().trim().isEmpty()) {
                it.error = Constants.REQUIRED_MESSAGE
                it.requestFocus()
                false
            } else {
                true
            }
        }

        val removeError: (TextInputEditText) -> Unit = {
            it.error = null
        }
    }

}