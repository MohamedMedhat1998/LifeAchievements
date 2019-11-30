package com.andalus.lifeachievements.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andalus.lifeachievements.R
import com.andalus.lifeachievements.models.MiniUser
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_user.view.*

class SearchResultsAdapter(private val data: MutableList<MiniUser>) :
    RecyclerView.Adapter<SearchResultsAdapter.ResultHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultHolder {
        context = parent.context
        return ResultHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_user,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ResultHolder, position: Int) {
        Glide.with(context)
            .load(data[position].picture)
            .apply(RequestOptions.centerCropTransform())
            .into(holder.ivProfilePicture)
        holder.tvName.text = data[position].name
        holder.tvUsername.text =
            context.getString(R.string.username_placeholder, data[position].username)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ResultHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivProfilePicture: ImageView = itemView.ivProfilePicture
        val tvName: TextView = itemView.tvName
        val tvUsername: TextView = itemView.tvUsername
    }
}