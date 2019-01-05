package com.amishgarg.wartube.Adapter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigator
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import com.amishgarg.wartube.Activity.MainActivity
import com.amishgarg.wartube.Activity.PostsFragment
import com.amishgarg.wartube.FirebaseUtil
import com.amishgarg.wartube.GlideUtil
import com.amishgarg.wartube.Model.Post
import com.amishgarg.wartube.R
import com.amishgarg.wartube.TimeDisplay
import com.amishgarg.wartube.ViewModels.PostDetailViewModel
import com.amishgarg.wartube.ViewModels.PostDetailViewModelFactory
import com.amishgarg.wartube.ViewModels.PostsViewModel
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.posts_item.view.*
import java.util.HashMap


class PostListAdapter : ListAdapter<Post, PostListAdapter.ItemViewholder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.posts_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PostListAdapter.ItemViewholder, position: Int) {
        holder.bind(getItem(position))
    }

    class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Post) = with(itemView) {
            // TODO: Bind the data with View
            this.post_author_name.text = item.author.display_name

            GlideUtil.loadImagePicasso(item.full__url, this.post_photo)

            GlideUtil.loadImagePicasso(item.author.profile_pic, this.post_author_icon)
            this.post_text.text = item.text
            this.post_timestamp.text = TimeDisplay(item.timestamp as Long).getTimeDisplay()
            this.post_num_likes.text = "${item.likes}"
            this.post_num_com.text = "${item.comments}"

            val postsKey = item.author.uid+item.timestamp

            this.like_button.setOnClickListener {
                val uMap = HashMap<String, Any>()
                Log.d("LikeButton", postsKey)
                uMap[FirebaseUtil.getPostsPath() + postsKey + "/likes"] = item.likes +1
                uMap[FirebaseUtil.getLikesPath() + postsKey + FirebaseUtil.getCurrentUserId()] = true
                FirebaseUtil.getBaseRef().updateChildren(uMap) { p0: DatabaseError?, p1: DatabaseReference ->
                   if (p0 != null) {
                       Log.e("upload", "cannot update database")
                       Log.e("upload", p0.toString())
                   }
               }
                this.post_num_likes.text = "${item.likes}"
            }

            this.comment_button.setOnClickListener {

            }

            setOnClickListener {

                val args : Bundle = Bundle()
                args.putString("postKey", postsKey)

                findNavController().navigate(R.id.post_detail_dest, args)
            }



        }


    }


}

class DiffCallback : DiffUtil.ItemCallback<Post>() {

    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return (oldItem.timestamp == newItem.timestamp && oldItem.author.uid == newItem.author.uid)
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}