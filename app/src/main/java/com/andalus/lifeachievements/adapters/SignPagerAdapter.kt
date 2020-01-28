package com.andalus.lifeachievements.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.andalus.lifeachievements.ui.sign.SignInFragment
import com.andalus.lifeachievements.ui.sign.SignUpFragment

class SignPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> SignInFragment.newInstance()
            1 -> SignUpFragment.newInstance()
            else -> SignInFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return 2
    }
}