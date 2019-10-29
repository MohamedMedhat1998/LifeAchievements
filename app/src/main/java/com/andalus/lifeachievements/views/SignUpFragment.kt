package com.andalus.lifeachievements.views

import android.os.Bundle
import android.util.Log
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
import kotlinx.android.synthetic.main.sign_up_fragment.*
import kotlinx.android.synthetic.main.sign_up_fragment.view.*

class SignUpFragment : Fragment() {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private lateinit var signUpViewModel: SignUpViewModel
    private lateinit var signActivityViewModel: SignActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.sign_up_fragment, container, false)

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
                    validateSimilarity(etPassword, etConfirmPassword)
                ) {
                    val gender: Gender = when (rgGender.checkedRadioButtonId) {
                        R.id.rbMale -> Gender.MALE
                        R.id.rbFemale -> Gender.FEMALE
                        else -> Gender.MALE
                    }
                    val user = User(
                        "",
                        etFirstName.text.toString(),
                        etLastName.text.toString(),
                        etEmail.text.toString(),
                        etPhone.text.toString(),
                        etUsername.text.toString(),
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

        signUpViewModel.errors.observe(this, Observer {
            Log.d("Observer", "onChanged")
            if (!it.isNullOrEmpty()) {
                it.forEach { pair ->
                    when (pair.first) {
                        Constants.ERROR_USERNAME -> {
                            etUsername.error = pair.second
                            etUsername.requestFocus()
                        }
                        Constants.ERROR_EMAIL -> {
                            etEmail.error = pair.second
                            etEmail.requestFocus()
                        }
                        Constants.ERROR_PHONE -> {
                            etPhone.error = pair.second
                            etPhone.requestFocus()
                        }
                        Constants.ERROR_FAILURE -> {
                            Toast.makeText(
                                view.context,
                                pair.second,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        })

        signUpViewModel.state.observe(this, Observer {
            when (it) {
                State.NormalState -> {
                    tvLoading.visibility = View.INVISIBLE
                    tvError.visibility = View.INVISIBLE
                    tvSuccess.visibility = View.INVISIBLE
                    tvNormal.visibility = View.VISIBLE
                    btnSignUp.isEnabled = true
                    signActivityViewModel.setSignedUp(false)
                }
                State.LoadingState -> {
                    tvLoading.visibility = View.VISIBLE
                    tvError.visibility = View.INVISIBLE
                    tvSuccess.visibility = View.INVISIBLE
                    tvNormal.visibility = View.INVISIBLE
                    btnSignUp.isEnabled = false
                    signActivityViewModel.setSignedUp(false)
                }
                State.SuccessState -> {
                    tvLoading.visibility = View.INVISIBLE
                    tvError.visibility = View.INVISIBLE
                    tvSuccess.visibility = View.VISIBLE
                    tvNormal.visibility = View.INVISIBLE
                    btnSignUp.isEnabled = true
                    signActivityViewModel.setSignedUp(true)
                }
                State.ErrorState -> {
                    tvLoading.visibility = View.INVISIBLE
                    tvError.visibility = View.VISIBLE
                    tvSuccess.visibility = View.INVISIBLE
                    tvNormal.visibility = View.INVISIBLE
                    btnSignUp.isEnabled = true
                    signActivityViewModel.setSignedUp(false)
                }
            }
        })
        return view
    }

    private fun validateNonEmpty(textInputEditText: TextInputEditText): Boolean {
        if (textInputEditText.text.isNullOrEmpty()) {
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
        if (validateNonEmpty(firstTextInputEditText) && validateNonEmpty(secondTextInputEditText)) {
            if (firstTextInputEditText.text.toString() != secondTextInputEditText.text.toString()) {
                firstTextInputEditText.error = getString(R.string.error_no_match)
                secondTextInputEditText.error = getString(R.string.error_no_match)
                firstTextInputEditText.requestFocus()
                return false
            }
            return true
        }
        return false
    }

}
