package com.htetarkarzaw.twitterlite.ui.screen.auth.splash

import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.htetarkarzaw.twitterlite.R
import com.htetarkarzaw.twitterlite.databinding.FragmentSplashBinding
import com.htetarkarzaw.twitterlite.ui.base.BaseFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    private lateinit var auth: FirebaseAuth
    override fun observe() {
    }

    override fun initUi() {
        auth = Firebase.auth
        val currentUser = auth.currentUser
        lifecycleScope.launch {
            delay(3000)
            if (currentUser != null) {
                Toast.makeText(requireContext(), "Welcome back ${currentUser.displayName}", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_splashFragment_to_feedFragment)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }
        }
    }
}