package com.htetarkarzaw.twitterlite.ui.screen.setting.editprofile

import android.Manifest
import android.net.Uri
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseUser
import com.htetarkarzaw.twitterlite.data.Resource
import com.htetarkarzaw.twitterlite.databinding.FragmentEditProfileBinding
import com.htetarkarzaw.twitterlite.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>(FragmentEditProfileBinding::inflate) {
    private val viewModel: EditProfileViewModel by viewModels()
    private var photoUri: Uri? = null
    override fun observe() {
        lifecycleScope.launch {
            viewModel.updateFlow.collectLatest {
                hideLoadingDialog()
                when (it) {
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), "Update Fail! $it", Toast.LENGTH_LONG).show()
                    }

                    is Resource.Loading -> {
                        showLoadingDialog("Updating...")
                    }

                    is Resource.Nothing -> {

                    }

                    is Resource.Success -> {
                        photoUri = null
                        viewModel.currentUser?.let { it1 -> bindProfile(it1) }
                    }
                }
            }
        }
    }

    override fun initUi() {
        viewModel.currentUser?.let { bindProfile(it) }
        binding.ivProfile.setOnClickListener {
            permissionTakeImageFromGallery.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        binding.btnSave.setOnClickListener {
            updateProfile()
        }
    }

    private fun bindProfile(user: FirebaseUser) {
        Glide.with(requireContext()).load(user.photoUrl).into(binding.ivProfile)
        binding.etDisplayName.setText(user.displayName ?: "")
        binding.etEmail.setText(user.email ?: "")
    }

    private fun updateProfile() {
        val name = binding.etDisplayName.text.toString()
        val email = binding.etEmail.text.toString()
        viewModel.updateUser(name, email, uri = photoUri)
    }

    private val permissionTakeImageFromGallery =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                takeImageFromGalleryProfile()
            }
        }

    private fun takeImageFromGalleryProfile() {
        viewLifecycleOwner.lifecycleScope.launch {
            fromGalleryProfile.launch("image/*")
        }
    }

    private val fromGalleryProfile = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        it?.let { uri ->
            photoUri = uri
            Glide.with(requireContext()).load(photoUri).into(binding.ivProfile)
        }
    }
}