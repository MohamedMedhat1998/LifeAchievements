package com.andalus.lifeachievements

import android.util.Log
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.andalus.lifeachievements.ui.home.HomeActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchButtonClickTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<HomeActivity> =
        ActivityScenarioRule(HomeActivity::class.java)


    @Test
    fun testClick() {
        Espresso.onView(ViewMatchers.withId(R.id.item_search)).perform(ViewActions.click())
        Log.d("TESTING","BEFORE TOOLBAR CLICK")
        Espresso.onView(ViewMatchers.withId(R.id.ibSearch)).perform(ViewActions.click())
        Log.d("TESTING","AFTER TOOLBAR CLICK")
    }
}