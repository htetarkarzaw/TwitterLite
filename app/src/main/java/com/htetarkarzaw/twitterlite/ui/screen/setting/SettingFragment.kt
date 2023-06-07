package com.htetarkarzaw.twitterlite.ui.screen.setting

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.htetarkarzaw.twitterlite.R
import com.htetarkarzaw.twitterlite.databinding.FragmentSettingBinding
import com.htetarkarzaw.twitterlite.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {

    private val viewModel: SettingViewModel by viewModels()

    override fun observe() {
        if (viewModel.currentUser != null) {
            binding.tvName.text = viewModel.currentUser!!.displayName
            binding.tvEmail.text = viewModel.currentUser!!.email
            binding.tvPhoneNumber.text = viewModel.currentUser!!.phoneNumber
            Glide.with(requireContext()).load(viewModel.currentUser!!.photoUrl)
                .placeholder(R.drawable.img_profile_place_holder)
                .into(binding.ivProfile)
        }
    }

    override fun initUi() {
        binding.cvLogout.setOnClickListener {
            viewModel.logout()
            Toast.makeText(requireContext(), "Logout Success!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_settingFragment_to_splashFragment)
        }

        binding.cvProfile.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_viewProfileFragment)
        }
    }

}