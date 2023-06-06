package com.htetarkarzaw.twitterlite.ui.screen.feed

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.htetarkarzaw.twitterlite.R
import com.htetarkarzaw.twitterlite.data.Resource
import com.htetarkarzaw.twitterlite.databinding.FragmentFeedBinding
import com.htetarkarzaw.twitterlite.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FeedFragment : BaseFragment<FragmentFeedBinding>(FragmentFeedBinding::inflate) {

    private val viewModel: FeedViewModel by viewModels()

    override fun observe() {
        lifecycleScope.launch() {
            viewModel.feeds.collectLatest {
                when (it) {
                    is Resource.Error -> {

                    }

                    is Resource.Loading -> {}
                    is Resource.Nothing -> {}
                    is Resource.Success -> {}
                }
            }
        }
    }

    override fun initUi() {
        binding.textHome.setOnClickListener {
//            viewModel.addFeed(FeedCriteria())
        }
        val fabButton = requireActivity().findViewById<FloatingActionButton>(R.id.fab)
        fabButton.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_uploadFeedFragment)
        }
    }

}