package com.andalus.lifeachievements.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andalus.lifeachievements.R
import com.andalus.lifeachievements.models.Badge
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_badge.view.*

class BadgesBoardAdapter(private val badges: List<Badge>) :
    RecyclerView.Adapter<BadgesBoardAdapter.BadgeViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BadgeViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_badge, parent, false)
        return BadgeViewHolder(view)
    }

    override fun onBindViewHolder(holder: BadgeViewHolder, position: Int) {
        holder.tvBadgeTitle.text = badges[position].title
        Glide.with(context).load(badges[position].icon).placeholder(R.drawable.ic_medal)
            .into(holder.ivBadgeIcon)
    }

    override fun getItemCount(): Int {
        return badges.size
    }

    inner class BadgeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivBadgeIcon: ImageView = itemView.ivBadgeIcon
        val tvBadgeTitle: TextView = itemView.tvBadgeTitle

        init {
            itemView.setOnClickListener {
                //TODO open achievement details screen
            }
        }
    }
}