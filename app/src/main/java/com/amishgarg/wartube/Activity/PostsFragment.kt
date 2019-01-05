package com.amishgarg.wartube.Activity


import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amishgarg.wartube.Adapter.PostListAdapter
import com.amishgarg.wartube.Model.Author
import com.amishgarg.wartube.Model.Post
import com.amishgarg.wartube.ViewModels.PostsViewModel
import com.amishgarg.wartube.R
import com.amishgarg.wartube.ViewModels.PostDetailViewModel

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.common.collect.Lists
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_posts.*


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


    val TAG = "PostsFragmentDebug"

    lateinit var progressBar: ProgressBar
    var user: FirebaseUser? = null
    lateinit var auth: FirebaseAuth
    lateinit var databaseReference: DatabaseReference
    private lateinit var recyclerView: RecyclerView
  //  private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var postsViewModel : PostsViewModel
    private lateinit var postsDetailViewModel: PostDetailViewModel
    private val mAdapter = PostListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        val view : View = inflater.inflate(R.layout.fragment_posts, container, false)

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser
        if(user == null)
        {
            findNavController().navigate(R.id.welcome_dest)
        }
        databaseReference = FirebaseDatabase.getInstance().getReference()

        progressBar = view.findViewById(R.id.progressBar_cyclic)
        val fab : FloatingActionButton = view.findViewById(R.id.fab)
        recyclerView = view.findViewById<RecyclerView>(R.id.posts_recycler)
        viewManager = LinearLayoutManager(context)
        fab.setOnClickListener {
            findNavController().navigate(R.id.new_post_dest)
        }
        recyclerView = view!!.findViewById<RecyclerView>(R.id.posts_recycler).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = mAdapter


        }


        return view

    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        postsViewModel = ViewModelProviders.of(this).get(PostsViewModel::class.java)
        progressBar_cyclic.visibility = View.VISIBLE
        postsViewModel.getPostsList().observe(this, Observer {
            //            Log.d("List100", it[0].author.display_name)
            Log.d("FirebaseQueryLiveData", "Observing")
            progressBar_cyclic.visibility = View.GONE
            mAdapter.submitList(Lists.reverse(it))
            mAdapter.notifyDataSetChanged()
        })




    }


}





















/*
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
*/


