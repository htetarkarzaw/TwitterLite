package com.htetarkarzaw.twitterlite.ui.screen.setting.editprofile

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseUser
import com.htetarkarzaw.twitterlite.R
import com.htetarkarzaw.twitterlite.data.Resource
import com.htetarkarzaw.twitterlite.databinding.FragmentEditProfileBinding
import com.htetarkarzaw.twitterlite.ui.base.BaseFragment
import com.htetarkarzaw.twitterlite.utils.Constants
import com.htetarkarzaw.twitterlite.utils.InputCheckerUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>(FragmentEditProfileBinding::inflate) {
    private val viewModel: EditProfileViewModel by activityViewModels()
    private var photoUri: Uri? = null
    override fun observe() {
        viewModel.currentUser?.let { bindProfile(it) }
        lifecycleScope.launch {
            viewModel.updateFlow.collectLatest {
                hideLoadingDialog()
                when (it) {
                    is Resource.Error -> {
                        errorDialog.setUpDialog("Update Fail! $it", true) {
                            updateProfile()
                            errorDialog.dismiss()
                        }
                    }

                    is Resource.Loading -> {
                        showLoadingDialog("Updating...")
                    }

                    is Resource.Nothing -> {}

                    is Resource.Success -> {
                        photoUri = null
                        viewModel.currentUser?.let { it1 -> bindProfile(it1) }
                        findNavController().popBackStack()
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.chooser.collectLatest {
                when (it) {
                    Constants.TAKE_GALLERY -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            permissionTakeImageFromGallery.launch(Manifest.permission.READ_MEDIA_IMAGES)
                        } else {
                            permissionTakeImageFromGallery.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }
                    }

                    Constants.TAKE_PHOTO -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            permissionTakePhoto.launch(Manifest.permission.READ_MEDIA_IMAGES)
                        } else {
                            permissionTakePhoto.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }
                    }
                }
            }
        }
    }

    override fun initUi() {
        binding.ivProfile.setOnClickListener {
            findNavController().navigate(R.id.action_editProfileFragment_to_imageChooserBsFragment)
        }
        binding.btnSave.setOnClickListener {
            updateProfile()
        }
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun bindProfile(user: FirebaseUser) {
        Glide.with(requireContext()).load(user.photoUrl).into(binding.ivProfile)
        binding.etDisplayName.setText(user.displayName ?: "")
        binding.etEmail.setText(user.email ?: "")
        photoUri = null
    }

    private fun updateProfile() {
        val name = binding.etDisplayName.text.toString()
        val email = binding.etEmail.text.toString()
        if (InputCheckerUtil.validateEmailAddress(email)) {
            binding.tilEmail.error = null
        } else {
            binding.tilEmail.error = "Please enter valid email!"
            return
        }
        if (name.isNotEmpty()) {
            binding.tilDisplayName.error = null
        } else {
            binding.tilDisplayName.error = "Please enter display name!"
            return
        }
        viewModel.updateUser(name, email, uri = photoUri)
    }

    private val permissionTakeImageFromGallery =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                takeImageFromGalleryProfile()
            }
        }

    private val permissionTakePhoto =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                takeImageFromCameraProfile()
            }
        }

    private fun takeImageFromGalleryProfile() {
        viewLifecycleOwner.lifecycleScope.launch {
            fromGalleryProfile.launch("image/*")
        }
    }

    private val fromCameraProfile = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) {
        if (it) {
            Glide.with(requireContext()).load(photoUri).into(binding.ivProfile)
        }

    }

    private fun takeImageFromCameraProfile() {
        viewLifecycleOwner.lifecycleScope.launch {
            getTmpFileUri().let {
                photoUri = it
                fromCameraProfile.launch(photoUri)
            }
        }
    }

    private fun getTmpFileUri(): Uri {
        val tmpFile =
            File.createTempFile("just_temp_file_for_profile", ".png", requireActivity().cacheDir).apply {
                createNewFile()
                deleteOnExit()
            }

        return FileProvider.getUriForFile(
            requireActivity(),
            "com.htetarkarzaw.twitterlite.provider",
            tmpFile
        )
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