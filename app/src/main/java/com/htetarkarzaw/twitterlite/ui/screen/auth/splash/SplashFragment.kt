package com.htetarkarzaw.twitterlite.ui.screen.auth.splash

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.htetarkarzaw.twitterlite.R
import com.htetarkarzaw.twitterlite.databinding.FragmentSplashBinding
import com.htetarkarzaw.twitterlite.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    private val viewModel: SplashViewModel by viewModels()
    override fun observe() {
        lifecycleScope.launch {
            delay(3000)
            if (viewModel.currentUser != null) {
                Toast.makeText(requireContext(), "Welcome back ${viewModel.currentUser?.displayName}", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_splashFragment_to_feedFragment)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }
        }
    }

    override fun initUi() {
    }
}