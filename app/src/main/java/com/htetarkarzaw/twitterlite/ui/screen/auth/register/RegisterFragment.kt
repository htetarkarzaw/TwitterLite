package com.htetarkarzaw.twitterlite.ui.screen.auth.register

import android.util.Log
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.htetarkarzaw.twitterlite.R
import com.htetarkarzaw.twitterlite.databinding.FragmentRegisterBinding
import com.htetarkarzaw.twitterlite.ui.base.BaseFragment


class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private lateinit var auth: FirebaseAuth

    override fun observe() {
    }

    override fun initUi() {
        auth = Firebase.auth
        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val displayName = binding.etDisplayName.text.toString()
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser

                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(displayName).build()

                    user!!.updateProfile(profileUpdates)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.e("Firebase", "Register->User profile updated.")
                            }
                        }
                    Toast.makeText(requireContext(), "Success!", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_registerFragment_to_feedFragment)
                } else {
                    Toast.makeText(requireContext(), "Authentication failed!", Toast.LENGTH_LONG).show()
                }
            }
        }

    }
}