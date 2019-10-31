package com.andalus.lifeachievements.utils

import android.util.Patterns
import android.widget.EditText

class Functions {

    companion object {
        val validatePhoneNumber: (EditText) -> Boolean = {
            if (Patterns.PHONE.matcher(it.text.toString().trim()).matches()) {
                true
            } else {
                it.error = Constants.INVALID_PHONE_NUMBER
                it.requestFocus()
                false
            }
        }

        val validateEmailAddress: (EditText) -> Boolean = {
            if (Patterns.EMAIL_ADDRESS.matcher(it.text.toString().trim()).matches()) {
                true
            } else {
                it.error = Constants.INVALID_EMAIL_ADDRESS
                it.requestFocus()
                false
            }
        }

        val validatePasswordLength: (EditText) -> Boolean = {
            if (it.text.toString().length < 6) {
                it.error = Constants.PASSWORD_LENGTH_MESSAGE
                it.requestFocus()
                false
            } else {
                true
            }
        }

        val validateSimilarity: (EditText, EditText) -> Boolean =
            { firstEditText, secondEditText ->
                if (firstEditText.text.toString() != secondEditText.text.toString()) {
                    firstEditText.error = Constants.PASSWORD_NO_MATCH_MESSAGE
                    secondEditText.error = Constants.PASSWORD_NO_MATCH_MESSAGE
                    firstEditText.requestFocus()
                    false
                } else {
                    true
                }
            }

        val validateNonEmpty: (EditText) -> Boolean = {
            if (it.text.toString().trim().isEmpty()) {
                it.error = Constants.REQUIRED_MESSAGE
                it.requestFocus()
                false
            } else {
                true
            }
        }

        val removeError: (EditText) -> Unit = {
            it.error = null
        }
    }

}