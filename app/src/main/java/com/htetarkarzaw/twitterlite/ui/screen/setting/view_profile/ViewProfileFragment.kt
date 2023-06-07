package com.htetarkarzaw.twitterlite.ui.screen.setting.view_profile

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseUser
import com.htetarkarzaw.twitterlite.R
import com.htetarkarzaw.twitterlite.databinding.FragmentViewProfileBinding
import com.htetarkarzaw.twitterlite.ui.base.BaseFragment
import com.htetarkarzaw.twitterlite.ui.screen.feed.FeedAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ViewProfileFragment : BaseFragment<FragmentViewProfileBinding>(FragmentViewProfileBinding::inflate) {
    private val viewModel: ViewProfileViewModel by activityViewModels()
    private val feedAdapter by lazy {
        FeedAdapter { clickDetail(it) }
    }

    private fun clickDetail(position: Int) {
        val feedVO = feedAdapter.getClickItem(position)
        val navigation = ViewProfileFragmentDirections.actionViewProfileFragmentToFeedDetailFragment(feedVO)
        findNavController().navigate(navigation)
    }

    override fun observe() {
        viewModel.currentUser?.let { bindProfile(it) }
        lifecycleScope.launch {
            viewModel.feeds.collectLatest { feedList ->
                feedList.let {
                    if (it.isNotEmpty()) {
                        feedAdapter.submitList(it)
                    }
                }
            }
        }
    }

    override fun initUi() {
        feedAdapter.apply {
            binding.rvFeed.layoutManager = LinearLayoutManager(requireContext())
            binding.rvFeed.adapter = this
        }
        binding.btnEdit.setOnClickListener {
            findNavController().navigate(R.id.action_viewProfileFragment_to_editProfileFragment)
        }
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun bindProfile(user: FirebaseUser) {
        Glide.with(requireContext()).load(user.photoUrl).into(binding.ivProfile)
        binding.tvName.text = user.displayName ?: ""
        binding.tvEmail.text = user.email ?: ""
    }

}