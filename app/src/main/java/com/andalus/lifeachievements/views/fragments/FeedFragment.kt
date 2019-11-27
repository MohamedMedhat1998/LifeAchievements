package com.andalus.lifeachievements.views.fragments


import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.andalus.lifeachievements.R
import com.andalus.lifeachievements.adapters.FeedAdapter
import com.andalus.lifeachievements.data.PostsDatabase
import com.andalus.lifeachievements.data.daos.PostObjectsDao
import com.andalus.lifeachievements.models.Post
import com.andalus.lifeachievements.models.PostObject
import com.andalus.lifeachievements.repositories.TokenRepository
import com.andalus.lifeachievements.utils.NetworkStateTracer
import com.andalus.lifeachievements.view_models.FeedViewModel
import com.andalus.lifeachievements.view_models_factories.FeedViewModelFactory
import kotlinx.android.synthetic.main.fragment_feed.view.*

class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_feed, container, false)
        view.rvFeed.layoutManager = LinearLayoutManager(context)
        val adapter = FeedAdapter(mutableListOf())
        view.rvFeed.adapter = adapter
        val viewModelFactory = FeedViewModelFactory(
            TokenRepository(context!!),
            NetworkStateTracer(context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager),
            PostsDatabase.getInstance(context!!)
        )
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(FeedViewModel::class.java)
        viewModel.posts.observe(this, Observer {
            view.rvFeed.adapter = FeedAdapter(it as MutableList<PostObject>)
        })
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = FeedFragment()
    }
}
