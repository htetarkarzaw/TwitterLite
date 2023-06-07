package com.htetarkarzaw.twitterlite.ui.screen.feed

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.htetarkarzaw.twitterlite.data.Resource
import com.htetarkarzaw.twitterlite.databinding.FragmentFeedBinding
import com.htetarkarzaw.twitterlite.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class FeedFragment : BaseFragment<FragmentFeedBinding>(FragmentFeedBinding::inflate) {

    private val viewModel: FeedViewModel by viewModels()
    private var feedCount = 0
    private val feedAdapter by lazy {
        FeedAdapter { clickDetail(it) }
    }

    override fun observe() {
        lifecycleScope.launch {
            viewModel.dbFeeds.collectLatest { feedList ->
                Timber.tag("hakz.feedscreen.feed.count").d(feedList.size.toString())
                feedList.let {
                    if (it.isNotEmpty()) {
                        feedAdapter.submitList(it)
                        if (feedCount < it.size) {
                            binding.rvFeed.smoothScrollToPosition(0)
                        }
                        binding.tvNoData.visibility = View.GONE
                    } else {
                        binding.tvNoData.visibility = View.VISIBLE
                    }
                    feedCount = it.size
                }
            }
        }
        lifecycleScope.launch {
            viewModel.feeds.collectLatest {
                binding.swipeRefresh.isRefreshing = false
                binding.progressBar.visibility = View.GONE
                when (it) {
                    is Resource.Error -> {

                    }

                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Resource.Nothing -> {}
                    is Resource.Success -> {
                        Timber.tag("hakz.feedscreen.feed.count").d(it.data)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFeeds()
    }

    override fun initUi() {
        feedAdapter.apply {
            binding.rvFeed.layoutManager = LinearLayoutManager(requireContext())
            binding.rvFeed.adapter = this
        }
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getFeeds()
        }
    }

    private fun clickDetail(position: Int) {
        val feedVO = feedAdapter.getClickItem(position)
        val navigation = FeedFragmentDirections.actionFeedFragmentToFeedDetailFragment(feedVO)
        findNavController().navigate(navigation)
    }

}