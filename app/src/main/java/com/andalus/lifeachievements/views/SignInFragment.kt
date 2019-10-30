package com.andalus.lifeachievements.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.andalus.lifeachievements.R
import com.andalus.lifeachievements.utils.Constants
import com.andalus.lifeachievements.view_models.SignActivityViewModel
import com.andalus.lifeachievements.view_models.SignInViewModel
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_in.view.*

class SignInFragment : Fragment() {

    companion object {
        fun newInstance() = SignInFragment()
    }

    private lateinit var signInViewModel: SignInViewModel
    private lateinit var signActivityViewModel: SignActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)

        signInViewModel = ViewModelProviders.of(this).get(SignInViewModel::class.java)
        signActivityViewModel =
            activity?.run { ViewModelProviders.of(this).get(SignActivityViewModel::class.java) }
                ?: throw Exception(Constants.ERROR_INVALID_ACTIVITY)

        signActivityViewModel.emailAddress.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                etUserEmailPhone.setText(it)
                etPassword.requestFocus()
                Log.d("login", "email changed")
            }
        })

        view.tvSignUp.setOnClickListener {
            signActivityViewModel.setNewSignUp(true)
        }

        return view
    }


}
