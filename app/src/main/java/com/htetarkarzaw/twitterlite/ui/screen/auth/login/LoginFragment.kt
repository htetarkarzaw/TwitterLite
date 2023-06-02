package com.htetarkarzaw.twitterlite.ui.screen.auth.login

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.htetarkarzaw.twitterlite.R
import com.htetarkarzaw.twitterlite.databinding.FragmentLoginBinding
import com.htetarkarzaw.twitterlite.ui.base.BaseFragment

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private lateinit var auth: FirebaseAuth

    override fun observe() {
    }

    override fun initUi() {
        auth = Firebase.auth
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    val currentUser = auth.currentUser
                    Toast.makeText(requireContext(), "Login Success! ${currentUser?.displayName}", Toast.LENGTH_LONG)
                        .show()
                    findNavController().navigate(R.id.action_loginFragment_to_feedFragment)
                } else {
                    Toast.makeText(requireContext(), "Login Fail! ${it.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
}