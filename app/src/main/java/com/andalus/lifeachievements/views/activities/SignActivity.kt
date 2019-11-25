package com.andalus.lifeachievements.views.activities

import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.andalus.lifeachievements.R
import com.andalus.lifeachievements.adapters.SignPagerAdapter
import com.andalus.lifeachievements.repositories.TokenRepository
import com.andalus.lifeachievements.utils.Constants
import com.andalus.lifeachievements.view_models.SignActivityViewModel
import com.andalus.lifeachievements.view_models_factories.SignActivityViewModelFactory
import kotlinx.android.synthetic.main.activity_sign.*

private const val SIGN_UP_FRAGMENT = 1
private const val SIGN_IN_FRAGMENT = 0

class SignActivity : AppCompatActivity() {

    private lateinit var signActivityViewModel: SignActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_sign)

        val viewModelFactory = SignActivityViewModelFactory(
            TokenRepository(
                this
            )
        )

        signActivityViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SignActivityViewModel::class.java)

        validateToken()

        vpSignActivity.adapter = SignPagerAdapter(supportFragmentManager)

        if (intent.hasExtra(Constants.EMAIL_KEY))
            signActivityViewModel.setEmailAddress(intent.extras!!.getString(Constants.EMAIL_KEY)!!)

        signActivityViewModel.completeSignedUp.observe(this, Observer {
            if (it)
                vpSignActivity.currentItem =
                    SIGN_IN_FRAGMENT
        })

        signActivityViewModel.newSignUp.observe(this, Observer {
            if (it)
                vpSignActivity.currentItem =
                    SIGN_UP_FRAGMENT
        })

    }

    private fun validateToken() {
        if (signActivityViewModel.token.isNotEmpty()) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }

}