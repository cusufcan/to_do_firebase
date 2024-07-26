package com.cusufcan.todofirebase.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.cusufcan.todofirebase.R
import com.google.firebase.auth.FirebaseAuth

class SplashFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        navController = Navigation.findNavController(view)

        Handler(Looper.myLooper()!!).postDelayed(
            {
                var action = SplashFragmentDirections.actionSplashFragmentToSignInFragment()
                auth.currentUser?.let {
                    action = SplashFragmentDirections.actionSplashFragmentToHomeFragment()
                }
                navController.navigate(action)
            },
            2000,
        )
    }
}