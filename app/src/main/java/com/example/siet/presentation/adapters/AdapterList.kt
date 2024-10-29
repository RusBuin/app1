package com.example.siet.presentation

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.paging.PagingDataAdapter
import com.bumptech.glide.Glide
import com.example.siet.R
import com.example.siet.domain.models.UserOfList

class AdapterList(
    private val context: Context,
    private val onFollowClick: (UserOfList) -> Unit,
    private val onUnfollowClick: (UserOfList) -> Unit
) : PagingDataAdapter<UserOfList, AdapterList.UserViewHolder>(UserDiffCallback()) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_user, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        user?.let { holder.bind(it) }
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userName: TextView = itemView.findViewById(R.id.userName)
        private val userEmail: TextView = itemView.findViewById(R.id.userEmail)
        private val userAvatar: ImageView = itemView.findViewById(R.id.userAvatar)
        private val buttonFollow: Button = itemView.findViewById(R.id.buttonFollow)

        fun bind(user: UserOfList) {
            Log.d("AdapterList", "Binding user: $user")

            userName.text = user.getFullName()
            userEmail.text = user.email
            Glide.with(itemView.context).load(user.avatar).into(userAvatar)

            val isFollowed = sharedPreferences.getBoolean("followed_${user.id}", false)
            buttonFollow.text = if (isFollowed) "Unfollow" else "Follow"

            buttonFollow.setOnClickListener {
                toggleFollow(user)
            }
        }

        private fun toggleFollow(user: UserOfList) {
            val editor = sharedPreferences.edit()
            val isFollowed = sharedPreferences.getBoolean("followed_${user.id}", false)

            if (isFollowed) {
                onUnfollowClick(user)
            } else {
                onFollowClick(user)
            }

            editor.putBoolean("followed_${user.id}", !isFollowed)
            editor.apply()
            notifyItemChanged(adapterPosition)
        }
    }

    class UserDiffCallback : DiffUtil.ItemCallback<UserOfList>() {
        override fun areItemsTheSame(oldItem: UserOfList, newItem: UserOfList): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserOfList, newItem: UserOfList): Boolean {
            return oldItem == newItem
        }
    }
}
