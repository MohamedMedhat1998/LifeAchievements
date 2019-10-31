package com.andalus.lifeachievements.views

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.andalus.lifeachievements.R
import com.andalus.lifeachievements.behaviors.CanValidateNonEmpty
import com.andalus.lifeachievements.utils.Constants
import com.andalus.lifeachievements.utils.Functions
import com.andalus.lifeachievements.view_models.VerificationActivityViewModel
import kotlinx.android.synthetic.main.activity_verification.*

class VerificationActivity : AppCompatActivity(), CanValidateNonEmpty {

    private lateinit var verificationActivityViewModel: VerificationActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)

        verificationActivityViewModel =
            ViewModelProviders.of(this).get(VerificationActivityViewModel::class.java)

        val email = intent.extras!!.getString(Constants.EMAIL_KEY)!!

        tvEmail.text = email

        btnVerify.setOnClickListener {
            if (validateNonEmpty(etVerification)) {
                verificationActivityViewModel.verify(email, etVerification.text.toString())
            }
        }

        verificationActivityViewModel.response.observe(this, Observer {
            it.errors.forEach { error ->
                Log.d(error.field, error.message)
            }
        })

    }

    override fun validateNonEmpty(editText: EditText): Boolean {
        return Functions.validateNonEmpty.invoke(editText)
    }
}
