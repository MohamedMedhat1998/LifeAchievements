package com.andalus.lifeachievements.ui.profile

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.andalus.lifeachievements.R
import com.andalus.lifeachievements.adapters.BadgesBoardAdapter
import com.andalus.lifeachievements.models.Badge
import com.andalus.lifeachievements.repositories.LoggedUserRepository
import com.andalus.lifeachievements.repositories.TokenRepository
import com.andalus.lifeachievements.ui.sign.SignActivity
import com.andalus.lifeachievements.utils.Constants
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.content_profile.*
import kotlin.math.ceil


//TODO distinguish between the owner profile activity and the other users profile activity
class ProfileActivity : AppCompatActivity() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var profileViewModelFactory: ProfileViewModelFactory

    private lateinit var followViewModel: FollowViewModel
    private lateinit var followViewModelFactory: FollowViewModelFacory

    val tokenRepository = TokenRepository(this)

    private var currentError = ""

    private var flagLoggedUser = false

    private lateinit var adapter: BadgesBoardAdapter

    private lateinit var penEdit: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(toolbar)
        //TODO move to Loading state
        toolbar_layout.title = getString(R.string.loading)

        val id = intent.extras?.getString(Constants.MINI_USER_ID_KEY)
        if (id == LoggedUserRepository(this).getUser().id)
            toggleLoggedUserProfile()

        //-------------------------
        /*
        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            Log.d("OFFSET",verticalOffset.toString())
            ObjectAnimator.ofFloat(penEdit, "TransitionX", verticalOffset.toFloat()).apply {
                start()
            }
            if(-verticalOffset == appBarLayout.totalScrollRange){
                Toast.makeText(baseContext,"Finish",Toast.LENGTH_SHORT).show()
            }else if(verticalOffset == 0){
                Toast.makeText(baseContext,"Start",Toast.LENGTH_SHORT).show()
            }
        })
        */
        //--------------------------
        if (!flagLoggedUser) {
            //--------PROFILE-----------
            profileViewModelFactory =
                ProfileViewModelFactory(
                    tokenRepository,
                    id!!
                )
            profileViewModel =
                ViewModelProviders.of(this, profileViewModelFactory)
                    .get(ProfileViewModel::class.java)

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

            profileViewModel.user.observe(this, Observer {
                if (!flagLoggedUser) {
                    toolbar_layout.title = getString(R.string.owner_name, it.firstName, it.lastName)
                    Glide.with(this)
                        .load(it.picture)
                        .placeholder(R.drawable.ic_man)
                        .apply(RequestOptions.centerCropTransform())
                        .into(ivProfilePicture)
                }
            })

            //--------FOLLOWING----------
            followViewModelFactory =
                FollowViewModelFacory(
                    tokenRepository
                )
            followViewModel =
                ViewModelProviders.of(this, followViewModelFactory).get(FollowViewModel::class.java)

            fab.setOnClickListener {
                followViewModel.follow(id)
            }

            followViewModel.response.observe(this, Observer {

            })

        }


        val fakeBadges = mutableListOf<Badge>()
        fakeBadges.add(
            Badge(
                "https://www.stickpng.com/assets/images/580b585b2edbce24c47b2af2.png",
                "GYM"
            )
        )
        fakeBadges.add(
            Badge(
                "https://www.stickpng.com/assets/images/580b585b2edbce24c47b2af2.png",
                "WAR"
            )
        )
        fakeBadges.add(
            Badge(
                "https://www.stickpng.com/assets/images/580b585b2edbce24c47b2af2.png",
                "SUCCESS"
            )
        )
        fakeBadges.add(
            Badge(
                "https://www.stickpng.com/assets/images/580b585b2edbce24c47b2af2.png",
                "DIET"
            )
        )
        fakeBadges.add(
            Badge(
                "https://www.stickpng.com/assets/images/580b585b2edbce24c47b2af2.png",
                "ADDICTION"
            )
        )
        fakeBadges.add(
            Badge(
                "https://www.stickpng.com/assets/images/580b585b2edbce24c47b2af2.png",
                "GAMING"
            )
        )
        fakeBadges.add(
            Badge(
                "https://www.stickpng.com/assets/images/580b585b2edbce24c47b2af2.png",
                "GAMING"
            )
        )
        fakeBadges.add(
            Badge(
                "https://www.stickpng.com/assets/images/580b585b2edbce24c47b2af2.png",
                "GAMING"
            )
        )
        fakeBadges.add(
            Badge(
                "https://www.stickpng.com/assets/images/580b585b2edbce24c47b2af2.png",
                "GAMING"
            )
        )
        fakeBadges.add(
            Badge(
                "https://www.stickpng.com/assets/images/580b585b2edbce24c47b2af2.png",
                "GAMING"
            )
        )
        fakeBadges.add(
            Badge(
                "https://www.stickpng.com/assets/images/580b585b2edbce24c47b2af2.png",
                "GAMING"
            )
        )
        fakeBadges.add(
            Badge(
                "https://www.stickpng.com/assets/images/580b585b2edbce24c47b2af2.png",
                "GAMING"
            )
        )
        fakeBadges.add(
            Badge(
                "https://www.stickpng.com/assets/images/580b585b2edbce24c47b2af2.png",
                "GAMING"
            )
        )
        fakeBadges.add(
            Badge(
                "https://www.stickpng.com/assets/images/580b585b2edbce24c47b2af2.png",
                "GAMING"
            )
        )


        adapter = BadgesBoardAdapter(fakeBadges)

        rvBadgesBoard.layoutManager = GridLayoutManager(this, 3)
        rvBadgesBoard.adapter = adapter


        //rvBadgesBoard.layoutParams = cardBadges.

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        //TODO dynamic span count
        val rvParams = rvBadgesBoard.layoutParams
        rvParams.height = (ceil(adapter.itemCount/3.0) * dpToPx(104f)).toInt()
        rvParams.width = WRAP_CONTENT
        rvBadgesBoard.layoutParams = rvParams

    }

    private fun dpToPx(dp:Float) : Float{
        val r: Resources = resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            r.displayMetrics
        )
    }


    private fun toggleLoggedUserProfile() {
        val loggedUser = LoggedUserRepository(this).getUser()
        flagLoggedUser = true
        //TODO fab
        fab.hide()
        //TODO edit profile button
        //TODO current active
        //TODO name, username, pic local
        toolbar_layout.title = loggedUser.name
        Glide.with(this)
            .load(loggedUser.picture)
            .placeholder(R.drawable.ic_man)
            .apply(RequestOptions.centerCropTransform())
            .into(ivProfilePicture)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_profile, menu)
        penEdit = menu?.findItem(R.id.item_edit_profile)!!
        if (!flagLoggedUser)
            penEdit.isVisible = false
        return super.onCreateOptionsMenu(menu)
    }

}
