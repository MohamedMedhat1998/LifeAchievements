package com.andalus.lifeachievements.views.activities

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.andalus.lifeachievements.R
import com.andalus.lifeachievements.behaviors.CanValidateNonEmpty
import com.andalus.lifeachievements.data.TokenRepository
import com.andalus.lifeachievements.models.Achievement
import com.andalus.lifeachievements.type.Type
import com.andalus.lifeachievements.utils.Functions
import com.andalus.lifeachievements.view_models.CreateChallengeViewModel
import com.andalus.lifeachievements.view_models_factories.CreateChallengeViewModelFactory
import kotlinx.android.synthetic.main.activity_create_challenge.*

class CreateChallengeActivity : AppCompatActivity(), CanValidateNonEmpty {

    private lateinit var viewModel: CreateChallengeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_challenge)

        val viewModelFactory = CreateChallengeViewModelFactory(TokenRepository(this))
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(CreateChallengeViewModel::class.java)

        btnCreate.setOnClickListener {

            if (validateNonEmpty(etTitle) && validateNonEmpty(etDescription)) {
                val title = etTitle.text.toString()
                val description = etDescription.text.toString()
                val days = spDaysCount.selectedItem.toString().toInt()
                val type = if (rbDoSomething.isChecked) Type.DO_IT else Type.GET_RID_OF
                val published = rbPublicChallenge.isChecked
                val achievement = Achievement(title, description, days, type, published)
                viewModel.createAchievement(achievement)
            }

        }


    }

    override fun validateNonEmpty(editText: EditText): Boolean {
        return Functions.validateNonEmpty.invoke(editText)
    }
}
