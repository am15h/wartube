package com.amishgarg.wartube.Activity

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.amishgarg.wartube.R
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class WelcomeFragment : Fragment() {

    private var mAuth: FirebaseAuth? = null
    private val RC_SIGN_IN = 2
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private val TAG = "WelcomeActivity"
    private val web_client_id = "96270419709-b9tkkuje7t89i55h0pj60ufnitrg55fn.apps.googleusercontent.com"

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signButton : Button = view.findViewById(R.id.sign_in_button)
        val firebaseAuth = FirebaseAuth.getInstance()



    }
}
