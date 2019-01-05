package com.amishgarg.wartube.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import com.amishgarg.wartube.livedata.FirebaseQueryLiveData
import com.amishgarg.wartube.FirebaseUtil
import com.amishgarg.wartube.Model.Post


class PostsViewModel : ViewModel() {

    val myTopPostsQuery = FirebaseUtil.getBaseRef().child("posts")
            .orderByChild("timestamp")

    private val liveData = FirebaseQueryLiveData(myTopPostsQuery)


    fun getPostsList() : LiveData<List<Post>>
    {
        Log.d("FirebaseQueryLiveData", "returning list")
        return liveData
    }



}