package com.amishgarg.wartube

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.DataSnapshot
import androidx.lifecycle.LiveData
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import com.amishgarg.wartube.Activity.FirebaseQueryLiveData
import com.amishgarg.wartube.Model.Post


class PostsViewModel : ViewModel() {

    val myTopPostsQuery = FirebaseUtil.getBaseRef().child("posts")
            .orderByChild("timestamp")

    private val liveData = FirebaseQueryLiveData(myTopPostsQuery)

    fun getDataSnapshotLiveData(): LiveData<DataSnapshot> {
        return liveData
    }

    fun getPostsList() : MutableLiveData<List<Post>>
    {
        return liveData.posts
    }



}