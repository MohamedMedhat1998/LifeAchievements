package com.andalus.lifeachievements.ui.sign_up_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.andalus.lifeachievements.R
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.sign_up_fragment.*
import kotlinx.android.synthetic.main.sign_up_fragment.view.*

class SignUpFragment : Fragment() {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.sign_up_fragment, container, false)
        view.btnSignUp.setOnClickListener {
            validateNonEmpty(etFirstName)
            validateNonEmpty(etLastName)
            validateNonEmpty(etEmail)
            validateNonEmpty(etPhone)
            validateNonEmpty(etUserName)
            validateNonEmpty(etPassword)
            validateNonEmpty(etConfirmPassword)
            validateSimilarity(etPassword,etConfirmPassword)
        }
        Log.d("Selected", view.spinnerCountries.selectedItem.toString())
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun validateNonEmpty(textInputEditText: TextInputEditText) : Boolean{
        if (textInputEditText.text.isNullOrEmpty()) {
            textInputEditText.error = getString(R.string.error_empty_field)
            return false
        }
        return true
    }

    private fun validateSimilarity(
        firstTextInputEditText: TextInputEditText,
        secondTextInputEditText: TextInputEditText
    ) {
        if(validateNonEmpty(firstTextInputEditText) &&  validateNonEmpty(secondTextInputEditText)){
            if(firstTextInputEditText.text.toString() != secondTextInputEditText.text.toString()){
                firstTextInputEditText.error = getString(R.string.error_no_match)
                secondTextInputEditText.error = getString(R.string.error_no_match)
            }
        }
    }

}
