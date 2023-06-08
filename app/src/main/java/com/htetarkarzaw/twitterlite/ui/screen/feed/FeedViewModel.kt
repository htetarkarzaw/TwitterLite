package com.htetarkarzaw.twitterlite.ui.screen.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.htetarkarzaw.twitterlite.data.Resource
import com.htetarkarzaw.twitterlite.data.firebase.vo.FeedVO
import com.htetarkarzaw.twitterlite.domain.usecase.GetFeedsUsecase
import com.htetarkarzaw.twitterlite.domain.usecase.RetrieveFeedsUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getFeedsUsecase: GetFeedsUsecase,
    private val retrieveFeedsUsecase: RetrieveFeedsUsecase
) : ViewModel() {

    private val _feeds = MutableStateFlow<Resource<String>>(Resource.Nothing())
    val feeds = _feeds.asStateFlow()

    private val _dbFeeds = MutableStateFlow<List<FeedVO>>(emptyList())
    val dbFeeds get() = _dbFeeds.asStateFlow()

    init {
        retrieveFeeds()
    }

    private fun retrieveFeeds() {
        viewModelScope.launch {
            retrieveFeedsUsecase().collectLatest {
                _dbFeeds.value = it
            }
        }
    }

    fun getFeeds() {
        _feeds.value = Resource.Loading()
        viewModelScope.launch {
            getFeedsUsecase().collectLatest {
                _feeds.value = it
            }
        }
    }
}