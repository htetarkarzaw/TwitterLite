package com.htetarkarzaw.twitterlite.ui.screen.setting

import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.htetarkarzaw.twitterlite.R
import com.htetarkarzaw.twitterlite.databinding.FragmentSettingBinding
import com.htetarkarzaw.twitterlite.ui.base.BaseFragment

class SettingFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {

    private val viewModel: SettingViewModel by viewModels()
    private lateinit var auth: FirebaseAuth

    override fun observe() {
        val textView: TextView = binding.textDashboard
        viewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
    }

    override fun initUi() {
        binding.btnLogout.setOnClickListener {
            auth = Firebase.auth
            val currentUser = auth.currentUser
            if (currentUser != null) {
                auth.signOut()
                Toast.makeText(requireContext(), "Logout Success!", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_settingFragment_to_splashFragment)
            }
        }
    }

}