package com.htetarkarzaw.twitterlite.ui.screen.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.htetarkarzaw.twitterlite.data.Resource
import com.htetarkarzaw.twitterlite.domain.criteria.FeedCriteria
import com.htetarkarzaw.twitterlite.domain.usecase.addFeedsUsecase
import com.htetarkarzaw.twitterlite.domain.usecase.getFeedsUsecase
import com.htetarkarzaw.twitterlite.domain.usecase.updateUserUsecase
import com.htetarkarzaw.twitterlite.domain.vo.FeedVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val getFeedsUsecase: getFeedsUsecase,private val addFeedsUsecase: addFeedsUsecase): ViewModel() {

    private val _feeds = MutableStateFlow<Resource<List<FeedVO>>>(Resource.Nothing())
    val feeds: StateFlow<Resource<List<FeedVO>>> = _feeds

    init {
//        addFeed()
        getFeeds()
    }

     fun addFeed(feedCriteria: FeedCriteria) {
        viewModelScope.launch {
            addFeedsUsecase(feedCriteria).collectLatest {
                when(it){
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
                    }
                }
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