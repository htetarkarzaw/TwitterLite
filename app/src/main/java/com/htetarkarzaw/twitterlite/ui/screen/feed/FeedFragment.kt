package com.htetarkarzaw.twitterlite.ui.screen.feed

import android.widget.TextView
import androidx.fragment.app.viewModels
import com.htetarkarzaw.twitterlite.databinding.FragmentFeedBinding
import com.htetarkarzaw.twitterlite.ui.base.BaseFragment

class FeedFragment : BaseFragment<FragmentFeedBinding>(FragmentFeedBinding::inflate) {

    private val viewModel: FeedViewModel by viewModels()

    override fun observe() {
        val textView: TextView = binding.textHome
        viewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
    }

    override fun initUi() {

    }

}