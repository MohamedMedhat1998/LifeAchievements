package com.andalus.lifeachievements.views.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.andalus.lifeachievements.R
import com.andalus.lifeachievements.behaviors.CanResetErrors
import com.andalus.lifeachievements.behaviors.CanValidateNonEmpty
import com.andalus.lifeachievements.enums.State
import com.andalus.lifeachievements.repositories.LoggedUserRepository
import com.andalus.lifeachievements.repositories.TokenRepository
import com.andalus.lifeachievements.utils.Constants
import com.andalus.lifeachievements.utils.Functions
import com.andalus.lifeachievements.view_models.SignActivityViewModel
import com.andalus.lifeachievements.view_models.SignInViewModel
import com.andalus.lifeachievements.view_models_factories.SignActivityViewModelFactory
import com.andalus.lifeachievements.view_models_factories.SignInViewModelFactory
import com.andalus.lifeachievements.views.activities.HomeActivity
import com.andalus.lifeachievements.views.activities.VerificationActivity
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_in.view.*

class SignInFragment : Fragment(), CanValidateNonEmpty, CanResetErrors {

    companion object {
        fun newInstance() = SignInFragment()
    }

    private lateinit var signInViewModel: SignInViewModel
    private lateinit var signActivityViewModel: SignActivityViewModel

    private var currentError = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)

        val tokenRepository =
            TokenRepository(context!!)

        val userRepository = LoggedUserRepository(context!!)

        val signInViewModelFactory = SignInViewModelFactory(tokenRepository, userRepository)

        signInViewModel =
            ViewModelProviders.of(this, signInViewModelFactory).get(SignInViewModel::class.java)

        val signActivityViewModelFactory = SignActivityViewModelFactory(tokenRepository)

        signActivityViewModel =
            activity?.run {
                ViewModelProviders.of(this, signActivityViewModelFactory)
                    .get(SignActivityViewModel::class.java)
            }
                ?: throw Exception(Constants.ERROR_INVALID_ACTIVITY)

        signActivityViewModel.emailAddress.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                etUserEmailPhone.setText(it)
                etPassword.requestFocus()
            }
        })

        view.tvSignUp.setOnClickListener {
            signActivityViewModel.setNewSignUp(true)
        }

        view.btnLogin.setOnClickListener {
            if (validateNonEmpty(etUserEmailPhone) && validateNonEmpty(etPassword)) {
                signInViewModel.signIn(etUserEmailPhone.text.toString(), etPassword.text.toString())
                resetErrors(etUserEmailPhone, etPassword)
            }
        }

        signInViewModel.response.observe(this, Observer {
            Log.d("Sign In", "Observed")
            it.errors.forEach { error ->
                when (error.field) {
                    Constants.ERROR_FAILURE -> {
                        currentError = error.message
                        Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
                    }
                    Constants.ERROR_UNVERIFIED -> {
                        currentError = ""
                        startActivity(Intent(activity, VerificationActivity::class.java).apply {
                            putExtra(
                                Constants.EMAIL_KEY,
                                etUserEmailPhone.text.toString()
                            )
                        })
                    }
                    Constants.ERROR_USERNAME -> {
                        currentError = ""
                        etUserEmailPhone.error = error.message
                        etUserEmailPhone.requestFocus()
                    }
                    Constants.ERROR_PASSWORD -> {
                        currentError = ""
                        etPassword.error = error.message
                        etUserEmailPhone.requestFocus()
                    }
                }
            }
        })

        signInViewModel.state.observe(this, Observer {
            when (it) {
                State.NormalState -> {
                    btnLogin.isEnabled = true
                    tvError.visibility = View.INVISIBLE
                    pbLoading.visibility = View.INVISIBLE
                }
                State.LoadingState -> {
                    btnLogin.isEnabled = false
                    tvError.visibility = View.INVISIBLE
                    pbLoading.visibility = View.VISIBLE
                }
                State.SuccessState -> {
                    Toast.makeText(
                        context,
                        getString(R.string.logged_in_complete),
                        Toast.LENGTH_LONG
                    ).show()
                    startActivity(Intent(activity, HomeActivity::class.java))
                    activity?.run { finish() }
                }
                State.ErrorState -> {
                    btnLogin.isEnabled = true
                    tvError.visibility = View.VISIBLE
                    tvError.text = currentError
                    pbLoading.visibility = View.INVISIBLE
                }
            }
        })

        return view
    }

    override fun validateNonEmpty(editText: EditText): Boolean {
        return Functions.validateNonEmpty.invoke(editText)
    }

    override fun resetErrors(vararg editText: EditText) {
        editText.forEach {
            Functions.removeError.invoke(it)
        }
    }

}
