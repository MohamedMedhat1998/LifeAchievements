package com.andalus.lifeachievements.views.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.andalus.lifeachievements.R
import com.andalus.lifeachievements.repositories.TokenRepository
import com.andalus.lifeachievements.utils.Constants
import com.andalus.lifeachievements.view_models.FollowViewModel
import com.andalus.lifeachievements.view_models.ProfileViewModel
import com.andalus.lifeachievements.view_models_factories.FollowViewModelFacory
import com.andalus.lifeachievements.view_models_factories.ProfileViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var profileViewModelFactory: ProfileViewModelFactory

    private lateinit var followViewModel: FollowViewModel
    private lateinit var followViewModelFactory: FollowViewModelFacory

    val tokenRepository = TokenRepository(this)

    private var currentError = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(toolbar)
        //TODO move to Loading state
        toolbar_layout.title = getString(R.string.loading)
        //-------------------------
        /*
        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            Log.d("OFFSET",verticalOffset.toString())
            ObjectAnimator.ofFloat(ivProfilePicture, "translationX", verticalOffset.toFloat()).apply {
                start()
            }
            if(-verticalOffset == appBarLayout.totalScrollRange){
                Toast.makeText(baseContext,"Finish",Toast.LENGTH_SHORT).show()
            }else if(verticalOffset == 0){
                Toast.makeText(baseContext,"Start",Toast.LENGTH_SHORT).show()
            }
        })*/

        val id = intent.extras?.getString(Constants.MINI_USER_ID_KEY)

        profileViewModelFactory = ProfileViewModelFactory(tokenRepository, id!!)
        profileViewModel =
            ViewModelProviders.of(this, profileViewModelFactory).get(ProfileViewModel::class.java)

        followViewModelFactory = FollowViewModelFacory(tokenRepository)
        followViewModel =
            ViewModelProviders.of(this, followViewModelFactory).get(FollowViewModel::class.java)

        profileViewModel.response.observe(this, Observer {
            if (it.errors.isNotEmpty()) {
                it.errors.forEach { error ->
                    when (error.field) {
                        Constants.ERROR_INVALID_TOKEN -> {
                            currentError = ""
                            profileViewModel.refreshWithNewToken(error.message)
                            Log.e("TOKEN", "refreshing with a new token :${error.message}")
                        }
                        Constants.ERROR_INVALID_USER -> {
                            currentError = error.message
                            profileViewModel.resetToken()
                            Toast.makeText(
                                this,
                                getString(R.string.session_expired),
                                Toast.LENGTH_LONG
                            )
                                .show()
                            startActivity(Intent(this, SignActivity::class.java).apply {
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            })
                            finish()
                        }
                    }
                }
            }
        })

        fab.setOnClickListener {
            followViewModel.follow(id)
        }

        followViewModel.response.observe(this, Observer {

        })

        profileViewModel.user.observe(this, Observer {
            toolbar_layout.title = getString(R.string.owner_name, it.firstName, it.lastName)
            Glide.with(this)
                .load(it.picture)
                .placeholder(R.drawable.ic_man)
                .apply(RequestOptions.centerCropTransform())
                .into(ivProfilePicture)
        })
    }
}
