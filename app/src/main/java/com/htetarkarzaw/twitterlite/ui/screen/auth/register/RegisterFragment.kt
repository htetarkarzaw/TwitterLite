package com.htetarkarzaw.twitterlite.ui.screen.auth.register

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.htetarkarzaw.twitterlite.R
import com.htetarkarzaw.twitterlite.data.Resource
import com.htetarkarzaw.twitterlite.databinding.FragmentRegisterBinding
import com.htetarkarzaw.twitterlite.ui.base.BaseFragment
import com.htetarkarzaw.twitterlite.utils.InputCheckerUtil
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
                        errorDialog.setUpDialog("Authentication failed! $it",false){
                            errorDialog.dismiss()
                        }
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
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()
            val displayName = binding.etDisplayName.text.toString().trim()
            if (InputCheckerUtil.validateEmailAddress(email)) {
                binding.tilEmail.error = null
            } else {
                binding.tilEmail.error = "Please enter valid email!"
                return@setOnClickListener
            }

            if (displayName.isNotEmpty()) {
                binding.tilDisplayName.error = null
            } else {
                binding.tilDisplayName.error = "Please enter display name!"
                return@setOnClickListener
            }

            if (InputCheckerUtil.validatePassword(password)) {
                binding.tilPassword.error = null
            } else {
                binding.tilPassword.error = "Please enter at least 8!"
                return@setOnClickListener
            }

            if (InputCheckerUtil.isSamePassword(password,confirmPassword)) {
                binding.tilConfirmPassword.error = null
            } else {
                binding.tilConfirmPassword.error = "Confirm password and password must be the same!"
                return@setOnClickListener
            }

            viewModel.registerWithEmailAndPassword(displayName, email, password)
        }
    }
}