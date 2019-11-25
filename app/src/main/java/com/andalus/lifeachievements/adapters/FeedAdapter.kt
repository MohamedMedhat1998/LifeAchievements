package com.andalus.lifeachievements.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andalus.lifeachievements.R
import com.andalus.lifeachievements.models.Post
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_achievement_post.view.*

class FeedAdapter(private val posts: MutableList<Post>) :
    RecyclerView.Adapter<FeedAdapter.PostHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        context = parent.context
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_achievement_post, parent, false)
        return PostHolder(view)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        /*Glide.with(context).load(posts[position].owner.picture).placeholder(R.drawable.ic_man)
            .into(holder.ivOwnerAvatar)
        holder.tvOwnerName.text = context.getString(
            R.string.owner_name,
            posts[position].owner.firstName,
            posts[position].owner.lastName
        )
        holder.tvOwnerUsername.text = posts[position].owner.username
        holder.tvPostDate.text = posts[position].date
        //TODO change the state photo
        /*if (posts[position].achievement.state == ""){
            holder.ivAchievementState.setImageResource()
        }else{

        }*/
        //Glide.with(context).load(posts[position].achievement.badge).into(holder.ivAchievementBadge)
        holder.tvAchievementDescription.text = posts[position].achievement.description
        holder.tvAchievementTitle.text = posts[position].achievement.title

        if (posts[position].likes.isNotEmpty())
            holder.tvPostLikes.text =
                context.getString(R.string.post_likes, posts[position].likes.size)

        if (posts[position].comments.isNotEmpty())
            holder.tvPostComments.text =
                context.getString(R.string.post_comments, posts[position].comments.size)

        if (posts[position].shares.isNotEmpty())
            holder.tvPostShares.text =
                context.getString(R.string.post_shares, posts[position].shares.size)
    */
    }

    inner class PostHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivOwnerAvatar: ImageView = itemView.ivOwnerAvatar
        val tvOwnerName: TextView = itemView.tvOwnerName
        val tvOwnerUsername: TextView = itemView.tvOwnerUsername
        val tvPostDate: TextView = itemView.tvPostDate
        val ivAchievementState = itemView.ivAchievementState
        val ivAchievementBadge: ImageView = itemView.ivAchievementBadge
        val tvAchievementDescription: TextView = itemView.tvAchievementDescription
        val tvAchievementTitle: TextView = itemView.tvAchievementTitle
        val tvPostLikes: TextView = itemView.tvPostLikes
        val tvPostComments: TextView = itemView.tvPostComments
        val tvPostShares: TextView = itemView.tvPostShares
    }
}