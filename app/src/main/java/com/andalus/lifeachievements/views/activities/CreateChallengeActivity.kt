package com.andalus.lifeachievements.views.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.andalus.lifeachievements.R
import com.andalus.lifeachievements.behaviors.CanResetErrors
import com.andalus.lifeachievements.behaviors.CanValidateNonEmpty
import com.andalus.lifeachievements.enums.State
import com.andalus.lifeachievements.models.Achievement
import com.andalus.lifeachievements.repositories.TokenRepository
import com.andalus.lifeachievements.type.Type
import com.andalus.lifeachievements.utils.Constants
import com.andalus.lifeachievements.utils.Functions
import com.andalus.lifeachievements.view_models.CreateChallengeViewModel
import com.andalus.lifeachievements.view_models_factories.CreateChallengeViewModelFactory
import kotlinx.android.synthetic.main.activity_create_challenge.*

class CreateChallengeActivity : AppCompatActivity(), CanValidateNonEmpty, CanResetErrors {

    private lateinit var viewModel: CreateChallengeViewModel
    private var currentError = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_challenge)

        val viewModelFactory = CreateChallengeViewModelFactory(
            TokenRepository(
                this
            )
        )
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(CreateChallengeViewModel::class.java)

        btnCreate.setOnClickListener {

            if (validateNonEmpty(etTitle) && validateNonEmpty(etDescription)) {
                resetErrors(etTitle, etDescription)
                val title = etTitle.text.toString()
                val description = etDescription.text.toString()
                val days = spDaysCount.selectedItem.toString().toInt()
                val type = if (rbDoSomething.isChecked) Type.DO_IT else Type.GET_RID_OF
                val published = rbPublicChallenge.isChecked
                val achievement =
                    Achievement("", title, description, days, type.toString(), published)
                viewModel.createAchievement(achievement)
            }

        }

        viewModel.response.observe(this, Observer {
            it.errors.forEach { error ->
                when (error.field) {
                    Constants.ERROR_INVALID_TOKEN -> {
                        currentError = ""
                        viewModel.refreshWithNewToken(error.message)
                        Log.e("TOKEN", "refreshing with a new token :${error.message}")
                    }
                    Constants.ERROR_INVALID_USER -> {
                        currentError = error.message
                        viewModel.resetToken()
                        Toast.makeText(this, getString(R.string.session_expired), Toast.LENGTH_LONG)
                            .show()
                        startActivity(Intent(this, SignActivity::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        })
                        finish()
                    }
                    Constants.ERROR_FAILURE -> {
                        Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
                        currentError = error.message
                    }
                    Constants.ERROR_TITLE -> {
                        etTitle.error = error.message
                        currentError = error.message
                    }
                    Constants.ERROR_DESCRIPTION -> {
                        etDescription.error = error.message
                        currentError = error.message
                    }
                }
            }
        })

        viewModel.state.observe(this, Observer {
            when (it) {
                State.NormalState -> {
                    pbLoading.visibility = View.INVISIBLE
                    tvError.visibility = View.INVISIBLE
                    btnCreate.isEnabled = true
                }
                State.LoadingState -> {
                    pbLoading.visibility = View.VISIBLE
                    tvError.visibility = View.INVISIBLE
                    btnCreate.isEnabled = false
                }
                State.SuccessState -> {
                    pbLoading.visibility = View.INVISIBLE
                    tvError.visibility = View.INVISIBLE
                    btnCreate.isEnabled = true
                    Toast.makeText(
                        this,
                        getString(R.string.challenge_created_successfully),
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                }
                State.ErrorState -> {
                    pbLoading.visibility = View.INVISIBLE
                    tvError.visibility = View.VISIBLE
                    btnCreate.isEnabled = true
                    tvError.text = currentError
                }
            }
        })

    }

    override fun validateNonEmpty(editText: EditText): Boolean {
        return Functions.validateNonEmpty.invoke(editText)
    }

    override fun resetErrors(vararg editText: EditText) {
        editText.forEach {
            Functions.removeError(it)
        }
    }
}
