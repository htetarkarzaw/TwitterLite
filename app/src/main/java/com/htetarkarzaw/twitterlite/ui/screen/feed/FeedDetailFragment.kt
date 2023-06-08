package com.htetarkarzaw.twitterlite.ui.screen.feed

import android.annotation.SuppressLint
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.htetarkarzaw.twitterlite.R
import com.htetarkarzaw.twitterlite.data.Resource
import com.htetarkarzaw.twitterlite.data.firebase.vo.FeedVO
import com.htetarkarzaw.twitterlite.databinding.FragmentFeedDetailBinding
import com.htetarkarzaw.twitterlite.ui.base.BaseFragment
import com.htetarkarzaw.twitterlite.ui.component.ConfirmDialog
import com.htetarkarzaw.twitterlite.utils.DateTimeUtil.dateFormatForDetail
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FeedDetailFragment : BaseFragment<FragmentFeedDetailBinding>(FragmentFeedDetailBinding::inflate) {
    private val args: FeedDetailFragmentArgs by navArgs()
    private lateinit var feedVO: FeedVO
    private lateinit var optionMenu: MenuPopupHelper
    private val viewModel: FeedDetailViewModel by viewModels()
    val confirmDialog: ConfirmDialog by lazy { ConfirmDialog(requireContext()) }
    override fun observe() {
        if ((viewModel.currentUser?.uid ?: "") == feedVO.userId) {
            binding.ivMenu.visibility = View.VISIBLE
        } else {
            binding.ivMenu.visibility = View.GONE
        }

        lifecycleScope.launch {
            viewModel.deleteFeed.collectLatest {
                hideLoadingDialog()
                when (it) {
                    is Resource.Error -> {
                        errorDialog.setUpDialog(it.message.toString(), true) {
                            onDeleteConfirm()
                            errorDialog.dismiss()
                        }
                    }

                    is Resource.Loading -> {
                        showLoadingDialog("Deleting tweet!")
                    }

                    is Resource.Nothing -> {

                    }

                    is Resource.Success -> {
                        Toast.makeText(requireContext(), it.data, Toast.LENGTH_LONG).show()
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    @SuppressLint("RestrictedApi")
    override fun initUi() {
        feedVO = args.feedVO
        setUpFeed(feedVO)
        setUpForMenu()
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.ivMenu.setOnClickListener {
            optionMenu.show()
        }
        binding.ivTweetPhoto.setOnClickListener {
            binding.ivPhotoViewer.visibility = View.VISIBLE
            binding.ivClose.visibility = View.VISIBLE
        }

        binding.ivClose.setOnClickListener {
            binding.ivPhotoViewer.visibility = View.GONE
            binding.ivClose.visibility = View.GONE
        }

    }

    @SuppressLint("RestrictedApi")
    private fun setUpForMenu() {
        val menuBuilder = MenuBuilder(requireContext())
        val inflater = MenuInflater(requireContext())
        inflater.inflate(R.menu.detail_pop_menu, menuBuilder)
        optionMenu = MenuPopupHelper(
            requireContext(),
            menuBuilder, binding.ivMenu
        )
        optionMenu.setForceShowIcon(true)
        menuBuilder.setCallback(object : MenuBuilder.Callback {
            override fun onMenuItemSelected(menu: MenuBuilder, item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.delete -> {
                        confirmDialog.setUpDialog(
                            "Delete Tweet?",
                            "This can't be undone and it will be removed from your profile."
                        ) { onDeleteConfirm() }
                        return true
                    }

                    else -> return false
                }
            }

            override fun onMenuModeChange(menu: MenuBuilder) {

            }

        })
    }

    private fun onDeleteConfirm() {
        viewModel.deleteFeedById(feedVO)
    }

    private fun setUpFeed(feedVO: FeedVO) {
        Glide.with(requireContext())
            .load(feedVO.userProfileUrl)
            .placeholder(R.drawable.img_profile_place_holder)
            .into(binding.ivProfile)
        binding.tvName.text = feedVO.userName
        if (feedVO.tweet.isEmpty()) {
            binding.tvTweet.visibility = View.GONE
        } else {
            binding.tvTweet.visibility = View.VISIBLE
            binding.tvTweet.text = feedVO.tweet
        }
        if (feedVO.photoUrl != null && feedVO.photoUrl != "") {
            binding.ivTweetPhoto.visibility = View.VISIBLE
            Glide.with(requireContext()).load(feedVO.photoUrl)
                .placeholder(R.drawable.place_holder)
                .into(binding.ivTweetPhoto)
            Glide.with(requireContext())
                .load(feedVO.photoUrl)
                .placeholder(R.drawable.img_profile_place_holder)
                .into(binding.ivPhotoViewer)
        } else {
            binding.ivTweetPhoto.visibility = View.GONE
        }
        binding.tvTime.text = dateFormatForDetail(feedVO.date)
    }
}