package com.example.meetup.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.meetup.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashFragment : Fragment() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mAuth = Firebase.auth
        checkUser()
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private fun checkUser() {
        val currentUser = mAuth.currentUser
        Log.d("###", "$currentUser")
        if (currentUser == null) {
            findNavController().graph.startDestination = R.id.loginFragment
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        } else {
            findNavController().graph.startDestination = R.id.recyclerViewFragment
            findNavController().navigate(R.id.action_splashFragment_to_recyclerViewFragment)
        }
    }
}