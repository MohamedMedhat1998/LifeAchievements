package com.andalus.lifeachievements.views.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.andalus.lifeachievements.R
import com.andalus.lifeachievements.behaviors.CanValidateNonEmpty
import com.andalus.lifeachievements.enums.State
import com.andalus.lifeachievements.utils.Constants
import com.andalus.lifeachievements.utils.Functions
import com.andalus.lifeachievements.view_models.VerificationActivityViewModel
import kotlinx.android.synthetic.main.activity_verification.*

class VerificationActivity : AppCompatActivity(), CanValidateNonEmpty {

    private lateinit var verificationActivityViewModel: VerificationActivityViewModel

    private var currentError = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)

        verificationActivityViewModel =
            ViewModelProviders.of(this).get(VerificationActivityViewModel::class.java)

        val email = intent.extras!!.getString(Constants.EMAIL_KEY)!!

        tvEmail.text = email

        btnVerify.setOnClickListener {
            if (validateNonEmpty(etVerification)) {
                verificationActivityViewModel.verify(email, etVerification.text.toString().trim())
            }
        }

        verificationActivityViewModel.response.observe(this, Observer {
            it.errors.forEach { error ->
                when (error.field) {
                    Constants.ERROR_REGISTRATION_CODE -> {
                        etVerification.error = error.message
                        currentError = ""
                    }
                    Constants.ERROR_FAILURE -> {
                        Toast.makeText(baseContext, error.message, Toast.LENGTH_LONG).show()
                        currentError = error.message
                    }
                }
            }
        })

        verificationActivityViewModel.state.observe(this, Observer {
            when (it) {
                State.NormalState -> {
                    pbLoading.visibility = View.INVISIBLE
                    tvError.visibility = View.INVISIBLE
                    btnVerify.isEnabled = true
                }
                State.LoadingState -> {
                    pbLoading.visibility = View.VISIBLE
                    tvError.visibility = View.INVISIBLE
                    btnVerify.isEnabled = false
                }
                State.SuccessState -> {
                    Toast.makeText(
                        baseContext,
                        getString(R.string.account_verification_complete),
                        Toast.LENGTH_LONG
                    )
                        .show()
                    startActivity(
                        Intent(
                            this,
                            SignActivity::class.java
                        ).apply { putExtra(Constants.EMAIL_KEY, email) })
                    finish()
                }
                State.ErrorState -> {
                    pbLoading.visibility = View.INVISIBLE
                    tvError.visibility = View.VISIBLE
                    tvError.text = currentError
                    btnVerify.isEnabled = true
                }
            }
        })

    }

    override fun validateNonEmpty(editText: EditText): Boolean {
        return Functions.validateNonEmpty.invoke(editText)
    }
}
