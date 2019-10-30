package com.andalus.lifeachievements.views

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.andalus.lifeachievements.R
import com.andalus.lifeachievements.enums.State
import com.andalus.lifeachievements.models.User
import com.andalus.lifeachievements.type.Gender
import com.andalus.lifeachievements.utils.Constants
import com.andalus.lifeachievements.view_models.SignActivityViewModel
import com.andalus.lifeachievements.view_models.SignUpViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*

const val PASSWORD_MINIMUM_LENGTH = 6

class SignUpFragment : Fragment() {

    private var currentError = ""

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private lateinit var signUpViewModel: SignUpViewModel
    private lateinit var signActivityViewModel: SignActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        view.btnSignUp.setOnClickListener {
            if (::signUpViewModel.isInitialized) {
                if (
                    validateNonEmpty(etFirstName) &&
                    validateNonEmpty(etLastName) &&
                    validateNonEmpty(etEmail) &&
                    validateNonEmpty(etPhone) &&
                    validateNonEmpty(etUsername) &&
                    validateNonEmpty(etPassword) &&
                    validateNonEmpty(etConfirmPassword) &&
                    validatePasswordLength(etPassword) &&
                    validateSimilarity(etPassword, etConfirmPassword) &&
                    validatePhoneNumber(etPhone) &&
                    validateEmailAddress(etEmail)
                ) {
                    val gender: Gender = when (rgGender.checkedRadioButtonId) {
                        R.id.rbMale -> Gender.MALE
                        R.id.rbFemale -> Gender.FEMALE
                        else -> Gender.MALE
                    }
                    val user = User(
                        "",
                        etFirstName.text.toString().trim(),
                        etLastName.text.toString().trim(),
                        etEmail.text.toString().trim(),
                        etPhone.text.toString().trim(),
                        etUsername.text.toString().trim(),
                        gender,
                        "",
                        spinnerCountries.selectedItem.toString(),
                        etPassword.text.toString()
                    )
                    signUpViewModel.signUp(user)
                }
            }
        }

        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)
        signActivityViewModel =
            activity?.run { ViewModelProviders.of(this).get(SignActivityViewModel::class.java) }
                ?: throw Exception(Constants.ERROR_INVALID_ACTIVITY)

        signUpViewModel.response.observe(this, Observer {
            Log.d("Observer", "onChanged")
            if (!it.errors.isNullOrEmpty()) {
                it.errors.forEach { error ->
                    when (error.field) {
                        Constants.ERROR_USERNAME -> {
                            etUsername.error = error.message
                            etUsername.requestFocus()
                            currentError = ""
                        }
                        Constants.ERROR_EMAIL -> {
                            etEmail.error = error.message
                            etEmail.requestFocus()
                            currentError = ""
                        }
                        Constants.ERROR_PHONE -> {
                            etPhone.error = error.message
                            etPhone.requestFocus()
                            currentError = ""
                        }
                        Constants.ERROR_FAILURE -> {
                            Toast.makeText(
                                view.context,
                                error.message,
                                Toast.LENGTH_LONG
                            ).show()
                            currentError = error.message
                        }
                    }
                }
            }
        })

        signUpViewModel.state.observe(this, Observer {
            when (it) {
                State.NormalState -> {
                    pbLoading.visibility = View.INVISIBLE
                    tvError.visibility = View.INVISIBLE
                    btnSignUp.isEnabled = true
                    signActivityViewModel.setSignedUp(false)
                }
                State.LoadingState -> {
                    pbLoading.visibility = View.VISIBLE
                    tvError.visibility = View.INVISIBLE
                    btnSignUp.isEnabled = false
                    signActivityViewModel.setSignedUp(false)
                }
                State.SuccessState -> {
                    pbLoading.visibility = View.INVISIBLE
                    tvError.visibility = View.INVISIBLE
                    Toast.makeText(context, getString(R.string.sign_up_complete), Toast.LENGTH_LONG)
                        .show()
                    btnSignUp.isEnabled = true
                    signActivityViewModel.setSignedUp(true, etEmail.text.toString())
                }
                State.ErrorState -> {
                    pbLoading.visibility = View.INVISIBLE
                    tvError.visibility = View.VISIBLE
                    tvError.text = currentError
                    btnSignUp.isEnabled = true
                    signActivityViewModel.setSignedUp(false)
                }
            }
        })
        return view
    }

    private fun validateNonEmpty(textInputEditText: TextInputEditText): Boolean {
        if (textInputEditText.text.toString().trim().isEmpty()) {
            textInputEditText.error = getString(R.string.error_empty_field)
            textInputEditText.requestFocus()
            return false
        }
        return true
    }

    private fun validateSimilarity(
        firstTextInputEditText: TextInputEditText,
        secondTextInputEditText: TextInputEditText
    ): Boolean {
        if (firstTextInputEditText.text.toString() != secondTextInputEditText.text.toString()) {
            firstTextInputEditText.error = getString(R.string.error_no_match)
            secondTextInputEditText.error = getString(R.string.error_no_match)
            firstTextInputEditText.requestFocus()
            return false
        }
        return true
    }

    private fun validatePasswordLength(textInputEditText: TextInputEditText): Boolean {
        if (textInputEditText.text.toString().length < PASSWORD_MINIMUM_LENGTH) {
            textInputEditText.error = getString(R.string.password_minimum_length)
            textInputEditText.requestFocus()
            return false
        }
        return true
    }

    private fun validateEmailAddress(textInputEditText: TextInputEditText): Boolean {
        if (Patterns.EMAIL_ADDRESS.matcher(textInputEditText.text.toString().trim()).matches()) {
            return true
        }
        textInputEditText.error = getString(R.string.invalid_email)
        textInputEditText.requestFocus()
        return false
    }

    private fun validatePhoneNumber(textInputEditText: TextInputEditText): Boolean {
        if (Patterns.PHONE.matcher(textInputEditText.text.toString().trim()).matches()) {
            return true
        }
        textInputEditText.error = getString(R.string.invalid_phone)
        textInputEditText.requestFocus()
        return false
    }

}
