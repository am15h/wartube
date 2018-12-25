package com.amishgarg.wartube.Activity


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amishgarg.wartube.Adapter.PostsRecyclerAdapter
import com.amishgarg.wartube.FirebaseUtil
import com.amishgarg.wartube.Model.Author
import com.amishgarg.wartube.Model.Post
import com.amishgarg.wartube.R

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PostsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PostsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class PostsFragment : Fragment() {


    var user: FirebaseUser? = null
    lateinit var auth: FirebaseAuth
    lateinit var databaseReference: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser
        if(user == null)
        {
            findNavController().navigate(R.id.welcome_dest)
        }
        databaseReference = FirebaseDatabase.getInstance().getReference()

        return inflater.inflate(R.layout.fragment_posts, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fab : FloatingActionButton = view.findViewById(R.id.fab)
        recyclerView = view.findViewById<RecyclerView>(R.id.posts_recycler)
        viewManager = LinearLayoutManager(context)
        fab.setOnClickListener {
            findNavController().navigate(R.id.new_post_dest)
        }

        var posts: MutableList<Post> = ArrayList()
        posts.add(Post(Author("Belu","userid", "propic"), "", "dummy", "", System.currentTimeMillis()))
        posts.add(Post(Author("Belu2","userid2", "propic2"), "", "dummy2", "", System.currentTimeMillis()))

        viewAdapter = PostsRecyclerAdapter(posts)

        recyclerView = view.findViewById<RecyclerView>(R.id.posts_recycler).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }

        val myTopPostsQuery = databaseReference.child("posts")
                .orderByChild("timestamp")

        myTopPostsQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    val post = postSnapshot.getValue(Post::class.java)
                    Log.d("PostsLoading", post?.author?.display_name)
                    if (post != null) {
                        posts.add(post)
                        viewAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("PostsLoading", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })



    }



}
