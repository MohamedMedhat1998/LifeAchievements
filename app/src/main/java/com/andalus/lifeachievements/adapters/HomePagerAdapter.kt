package com.andalus.lifeachievements.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.andalus.lifeachievements.fragments.Feed
import com.andalus.lifeachievements.fragments.FriendSuggestions
import com.andalus.lifeachievements.fragments.Notifications

class HomePagerAdapter(fm:FragmentManager) : FragmentStatePagerAdapter(fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> Feed.newInstance("","")
            1 -> FriendSuggestions.newInstance("","")
            2 -> Notifications.newInstance("","")
            else -> Feed.newInstance("","")
        }
    }

    override fun getCount(): Int {
        return 3
    }

}