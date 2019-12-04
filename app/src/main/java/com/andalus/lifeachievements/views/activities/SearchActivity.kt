package com.andalus.lifeachievements.views.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.andalus.lifeachievements.R
import com.andalus.lifeachievements.adapters.SearchResultsAdapter
import com.andalus.lifeachievements.behaviors.CanValidateNonEmpty
import com.andalus.lifeachievements.enums.State
import com.andalus.lifeachievements.repositories.TokenRepository
import com.andalus.lifeachievements.utils.Constants
import com.andalus.lifeachievements.utils.Functions
import com.andalus.lifeachievements.view_models.SearchViewModel
import com.andalus.lifeachievements.view_models_factories.SearchViewModelFactory
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), CanValidateNonEmpty {

    private lateinit var adapter: SearchResultsAdapter
    private lateinit var viewModel: SearchViewModel
    private val viewModelFactory = SearchViewModelFactory(TokenRepository(this))

    private var currentError = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val toolbar = findViewById<Toolbar>(R.id.toolbarSearchActivity)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)

        adapter = SearchResultsAdapter(mutableListOf()) {
            startActivity(Intent(this, ProfileActivity::class.java).apply {
                putExtra(Constants.MINI_USER_ID_KEY, it.id)
            })
        }

        rvSearchedUsers.layoutManager = LinearLayoutManager(this)
        rvSearchedUsers.adapter = adapter

        ibSearch.setOnClickListener {
            if (validateNonEmpty(etSearch)) {
                viewModel.search(etSearch.text.toString())
                adapter.data.clear()
                adapter.notifyDataSetChanged()
            }
        }

        viewModel.response.observe(this, Observer {
            if (it.errors.isNotEmpty()) {
                it.errors.forEach { error ->
                    when (error.field) {
                        Constants.ERROR_INVALID_TOKEN -> {
                            currentError = ""
                            viewModel.refreshWithNewToken(error.message)
                            Log.e("TOKEN", "refreshing with a new token :${error.message}")
                        }
                        Constants.ERROR_INVALID_USER -> {
                            currentError = error.message
                            viewModel.resetToken()
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

        viewModel.usersList.observe(this, Observer {
            if (it.isNotEmpty()) {
                adapter.data.clear()
                adapter.data.addAll(it)
                adapter.notifyDataSetChanged()
            } else {
                tvError.text = getString(R.string.no_users_found)
                tvError.visibility = View.VISIBLE
            }
        })

        viewModel.state.observe(this, Observer {
            when (it) {
                State.NormalState -> {
                    pbLoading.visibility = View.INVISIBLE
                    tvError.visibility = View.INVISIBLE
                    ibSearch.isEnabled = true
                }
                State.LoadingState -> {
                    pbLoading.visibility = View.VISIBLE
                    tvError.visibility = View.INVISIBLE
                    ibSearch.isEnabled = false
                }
                State.SuccessState -> {
                    pbLoading.visibility = View.INVISIBLE
                    tvError.visibility = View.INVISIBLE
                    ibSearch.isEnabled = true
                }
                State.ErrorState -> {
                    pbLoading.visibility = View.INVISIBLE
                    tvError.visibility = View.VISIBLE
                    tvError.text = currentError
                    ibSearch.isEnabled = true
                }
            }
        })
    }

    override fun validateNonEmpty(editText: EditText): Boolean {
        return Functions.validateNonEmpty.invoke(editText)
    }
}
