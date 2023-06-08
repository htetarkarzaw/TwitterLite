package com.htetarkarzaw.twitterlite.ui.screen.auth.login

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.htetarkarzaw.twitterlite.R
import com.htetarkarzaw.twitterlite.data.Resource
import com.htetarkarzaw.twitterlite.databinding.FragmentLoginBinding
import com.htetarkarzaw.twitterlite.ui.base.BaseFragment
import com.htetarkarzaw.twitterlite.utils.InputCheckerUtil.validateEmailAddress
import com.htetarkarzaw.twitterlite.utils.InputCheckerUtil.validatePassword
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel: LoginViewModel by viewModels()
    override fun observe() {
        lifecycleScope.launch {
            viewModel.loginFlow.collectLatest {
                hideLoadingDialog()
                when (it) {
                    is Resource.Error -> {
                        errorDialog.setUpDialog("Login Fail! ${it.message}",false){
                            errorDialog.dismiss()
                        }
                    }

                    is Resource.Loading -> {
                        showLoadingDialog("Logging in...")
                    }

                    is Resource.Nothing -> {}
                    is Resource.Success -> {
                        val currentUser = it.data
                        Toast.makeText(
                            requireContext(),
                            "Login Success! ${currentUser?.displayName}",
                            Toast.LENGTH_LONG
                        )
                            .show()
                        findNavController().navigate(R.id.action_loginFragment_to_feedFragment)
                    }
                }
            }
        }
    }

    override fun initUi() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (validateEmailAddress(email)) {
                binding.tilEmail.error = null
            } else {
                binding.tilEmail.error = "Please enter valid email!"
                return@setOnClickListener
            }
            if (validatePassword(password)) {
                binding.tilPassword.error = null
            } else {
                binding.tilPassword.error = "Please enter at least 8!"
                return@setOnClickListener
            }
            hideSoftKeyboard()
            viewModel.loginWithEmailAndPassword(email, password)
        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
}