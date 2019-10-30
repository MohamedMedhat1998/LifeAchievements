package com.andalus.lifeachievements.views

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.andalus.lifeachievements.R
import com.andalus.lifeachievements.adapters.SignPagerAdapter
import com.andalus.lifeachievements.view_models.SignActivityViewModel
import kotlinx.android.synthetic.main.activity_sign.*

class SignActivity : AppCompatActivity() {

    private val SIGN_IN_FRAGMENT = 0
    private val SIGN_UP_FRAGMENT = 1

    private lateinit var signActivityViewModel: SignActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_sign)

        signActivityViewModel = ViewModelProviders.of(this).get(SignActivityViewModel::class.java)

        vpSignActivity.adapter = SignPagerAdapter(supportFragmentManager)

        signActivityViewModel.completeSignedUp.observe(this, Observer {
            if (it)
                vpSignActivity.currentItem = SIGN_IN_FRAGMENT
        })

        signActivityViewModel.newSignUp.observe(this, Observer {
            if (it)
                vpSignActivity.currentItem = SIGN_UP_FRAGMENT
        })
    }
}