package com.htetarkarzaw.twitterlite.ui.screen.setting.editprofile

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.htetarkarzaw.twitterlite.databinding.FragmentBsImageChooserBinding
import com.htetarkarzaw.twitterlite.ui.base.BaseBottomSheet
import com.htetarkarzaw.twitterlite.utils.Constants

class ImageChooserBsFragment : BaseBottomSheet<FragmentBsImageChooserBinding>(FragmentBsImageChooserBinding::inflate) {

    private val viewModel: EditProfileViewModel by activityViewModels()

    override fun setupView() {
        super.setupView()
        binding.ivClose.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnGallery.setOnClickListener {
            viewModel.updateChooser(Constants.TAKE_GALLERY)
            findNavController().popBackStack()
        }

        binding.btnTakePhoto.setOnClickListener {
            viewModel.updateChooser(Constants.TAKE_PHOTO)
            findNavController().popBackStack()
        }

    }
}