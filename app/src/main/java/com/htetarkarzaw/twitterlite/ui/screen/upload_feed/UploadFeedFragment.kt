package com.htetarkarzaw.twitterlite.ui.screen.upload_feed

import android.Manifest
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.htetarkarzaw.twitterlite.data.Resource
import com.htetarkarzaw.twitterlite.databinding.FragmentUploadFeedBinding
import com.htetarkarzaw.twitterlite.domain.criteria.FeedCriteria
import com.htetarkarzaw.twitterlite.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File

@AndroidEntryPoint
class UploadFeedFragment : BaseFragment<FragmentUploadFeedBinding>(FragmentUploadFeedBinding::inflate) {

    private val viewModel: UploadFeedViewModel by viewModels()
    private var photoUri: Uri? = null
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

    override fun observe() {
        lifecycleScope.launch {
            viewModel.addFeed.collectLatest {
                when (it) {
                    is Resource.Error -> {
                        Timber.tag("hakz.feedviewmodel").d(it.message)
                    }

                    is Resource.Loading -> {
                        Timber.tag("hakz.feedviewmodel").d("Loading")
                    }

                    is Resource.Nothing -> {
                        Timber.tag("hakz.feedviewmodel").d("nothing")
                    }

                    is Resource.Success -> {
                        Timber.tag("hakz.feedviewmodel").d("success")
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    override fun initUi() {
        binding.ivCamera.setOnClickListener {
            permissionTakePhoto.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        binding.ivGallery.setOnClickListener {
            permissionTakeImageFromGallery.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        binding.btnUpload.setOnClickListener {
            uploadFeed()
        }
        binding.tvCancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun uploadFeed() {
        val feedCriteria = FeedCriteria(
            tweet = binding.etTweet.text.toString(),
            photoUri = photoUri,
            date = System.currentTimeMillis()
        )
        viewModel.addFeed(feedCriteria)
    }

    private fun getTmpFileUri(): Uri {
        val tmpFile =
            File.createTempFile("just_temp_file_for_feed", ".png", requireActivity().cacheDir).apply {
                createNewFile()
                deleteOnExit()
            }

        return FileProvider.getUriForFile(
            requireActivity(),
            "com.htetarkarzaw.twitterlite.provider",
            tmpFile
        )
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