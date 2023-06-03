package com.htetarkarzaw.twitterlite.ui.screen.auth.register

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.htetarkarzaw.twitterlite.R
import com.htetarkarzaw.twitterlite.data.Resource
import com.htetarkarzaw.twitterlite.databinding.FragmentRegisterBinding
import com.htetarkarzaw.twitterlite.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()
    override fun observe() {
        lifecycleScope.launch {
            viewModel.registerFlow.collectLatest {
                hideLoadingDialog()
                when (it) {
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), "Authentication failed! $it", Toast.LENGTH_LONG).show()
                    }

                    is Resource.Loading -> {
                        showLoadingDialog("Registering...")
                    }

                    is Resource.Nothing -> {}

                    is Resource.Success -> {
                        Toast.makeText(requireContext(), "Success!", Toast.LENGTH_LONG).show()
                        findNavController().navigate(R.id.action_registerFragment_to_feedFragment)
                    }
                }
            }
        }
    }

    override fun initUi() {
        binding.btnRegister.setOnClickListener {
            hideSoftKeyboard()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val displayName = binding.etDisplayName.text.toString()
            viewModel.registerWithEmailAndPassword(displayName, email, password)
        }
    }
}