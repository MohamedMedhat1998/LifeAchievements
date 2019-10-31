package com.andalus.lifeachievements.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.andalus.lifeachievements.R
import com.andalus.lifeachievements.behaviors.*
import com.andalus.lifeachievements.enums.State
import com.andalus.lifeachievements.models.User
import com.andalus.lifeachievements.type.Gender
import com.andalus.lifeachievements.utils.Constants
import com.andalus.lifeachievements.utils.Functions
import com.andalus.lifeachievements.view_models.SignActivityViewModel
import com.andalus.lifeachievements.view_models.SignUpViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*

class SignUpFragment : Fragment(), CanValidatePhoneNumber, CanValidateEmailAddress,
    CanValidatePasswordLength, CanValidateSimilarity, CanValidateNonEmpty, CanResetErrors {


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

        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)
        signActivityViewModel =
            activity?.run { ViewModelProviders.of(this).get(SignActivityViewModel::class.java) }
                ?: throw Exception(Constants.ERROR_INVALID_ACTIVITY)

        view.btnSignUp.setOnClickListener {
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
                resetErrors(
                    etFirstName,
                    etLastName,
                    etEmail,
                    etPhone,
                    etUsername,
                    etPassword,
                    etConfirmPassword
                )
            }
        }

        signUpViewModel.response.observe(this, Observer {
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

    override fun validateNonEmpty(textInputEditText: TextInputEditText): Boolean {
        return Functions.validateNonEmpty.invoke(textInputEditText)
    }

    override fun validateSimilarity(
        firstTextInputEditText: TextInputEditText,
        secondTextInputEditText: TextInputEditText
    ): Boolean {
        return Functions.validateSimilarity.invoke(firstTextInputEditText, secondTextInputEditText)
    }

    override fun validatePasswordLength(textInputEditText: TextInputEditText): Boolean {
        return Functions.validatePasswordLength.invoke(textInputEditText)
    }

    override fun validateEmailAddress(textInputEditText: TextInputEditText): Boolean {
        return Functions.validateEmailAddress.invoke(textInputEditText)
    }

    override fun validatePhoneNumber(textInputEditText: TextInputEditText): Boolean {
        return Functions.validatePhoneNumber.invoke(textInputEditText)
    }

    override fun resetErrors(vararg textInputEditText: TextInputEditText) {
        textInputEditText.forEach {
            Functions.removeError.invoke(it)
        }
    }

}
