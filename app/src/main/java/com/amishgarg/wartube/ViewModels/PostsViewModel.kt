package com.amishgarg.wartube.ViewModels

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import com.amishgarg.wartube.Activity.MainActivity
import com.amishgarg.wartube.Adapter.PostsRecyclerAdapter
import com.amishgarg.wartube.livedata.FirebaseQueryLiveData
import com.amishgarg.wartube.FirebaseUtil
import com.amishgarg.wartube.Model.Post
import com.firebase.ui.database.FirebaseRecyclerOptions


class PostsViewModel: ViewModel() {

    val myTopPostsQuery = FirebaseUtil.getBaseRef().child("posts")
            .orderByChild("timestamp")


    val options : FirebaseRecyclerOptions<Post> = FirebaseRecyclerOptions.Builder<Post>()
            .setQuery(myTopPostsQuery, Post::class.java)
            .build()

    val mAdapter = PostsRecyclerAdapter(options)


    private val liveData = FirebaseQueryLiveData(myTopPostsQuery)


    fun getPostsList() : LiveData<List<Post>>
    {
        Log.d("FirebaseQueryLiveData", "returning list")
        return liveData
    }

    fun getApadter() : PostsRecyclerAdapter {
        return mAdapter
    }

}