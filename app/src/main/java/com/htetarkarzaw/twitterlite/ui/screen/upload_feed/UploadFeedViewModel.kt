package com.htetarkarzaw.twitterlite.ui.screen.upload_feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.htetarkarzaw.twitterlite.data.Resource
import com.htetarkarzaw.twitterlite.domain.criteria.FeedCriteria
import com.htetarkarzaw.twitterlite.domain.usecase.addFeedsUsecase
import com.htetarkarzaw.twitterlite.domain.usecase.getFeedsUsecase
import com.htetarkarzaw.twitterlite.domain.vo.FeedVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadFeedViewModel @Inject constructor(
    private val getFeedsUsecase: getFeedsUsecase,
    private val addFeedsUsecase: addFeedsUsecase
) : ViewModel() {

    private val _feeds = MutableStateFlow<Resource<List<FeedVO>>>(Resource.Nothing())
    val feeds: StateFlow<Resource<List<FeedVO>>> = _feeds

    private val _addFeed = MutableStateFlow<Resource<String>>(Resource.Nothing())
    val addFeed: StateFlow<Resource<String>> = _addFeed

    fun addFeed(feedCriteria: FeedCriteria) {
        viewModelScope.launch {
            addFeedsUsecase(feedCriteria).collectLatest {
                _addFeed.value = it
            }
        }
    }

    private fun getFeeds() {
        _feeds.value = Resource.Loading()
        viewModelScope.launch {
            getFeedsUsecase().collectLatest {

                _feeds.value = it
            }
        }
    }
}