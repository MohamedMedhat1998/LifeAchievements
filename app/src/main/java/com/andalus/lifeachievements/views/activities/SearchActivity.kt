package com.andalus.lifeachievements.views.activities

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.andalus.lifeachievements.R
import com.andalus.lifeachievements.adapters.SearchResultsAdapter
import com.andalus.lifeachievements.behaviors.CanValidateNonEmpty
import com.andalus.lifeachievements.repositories.TokenRepository
import com.andalus.lifeachievements.utils.Functions
import com.andalus.lifeachievements.view_models.SearchViewModel
import com.andalus.lifeachievements.view_models_factories.SearchViewModelFactory
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), CanValidateNonEmpty {

    private lateinit var adapter: SearchResultsAdapter
    private lateinit var viewModel: SearchViewModel
    private val viewModelFactory = SearchViewModelFactory(TokenRepository(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)

        adapter = SearchResultsAdapter(mutableListOf())

        rvSearchedUsers.layoutManager = LinearLayoutManager(this)
        rvSearchedUsers.adapter = adapter

        ibSearch.setOnClickListener {
            if (validateNonEmpty(etSearch))
                viewModel.search(etSearch.text.toString())
        }
    }

    override fun validateNonEmpty(editText: EditText): Boolean {
        return Functions.validateNonEmpty.invoke(editText)
    }
}
