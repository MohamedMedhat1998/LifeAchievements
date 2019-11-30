package com.andalus.lifeachievements.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.andalus.lifeachievements.R
import com.andalus.lifeachievements.adapters.HomePagerAdapter
import com.andalus.lifeachievements.views.activities.SearchActivity
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        /*val textView: TextView = root.findViewById(R.response.text_home)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })*/
        val pager = root.vpHome
        val tabLayout = root.tlHome
        pager.adapter = HomePagerAdapter(childFragmentManager)
        tabLayout.setupWithViewPager(pager)
        tabLayout.getTabAt(0)?.text = "Feed"
        tabLayout.getTabAt(1)?.text = "Friend Suggestions"
        tabLayout.getTabAt(2)?.text = "Notifications"
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_feed, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_search -> {
                startActivity(Intent(activity, SearchActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}