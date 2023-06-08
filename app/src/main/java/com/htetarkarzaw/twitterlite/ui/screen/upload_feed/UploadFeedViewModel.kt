package com.htetarkarzaw.twitterlite.ui.screen.upload_feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.htetarkarzaw.twitterlite.data.Resource
import com.htetarkarzaw.twitterlite.data.firebase.criteria.FeedCriteria
import com.htetarkarzaw.twitterlite.domain.usecase.AddFeedsUsecase
import com.htetarkarzaw.twitterlite.domain.usecase.GetFirebaseCurrentUserUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadFeedViewModel @Inject constructor(
    private val getCurrentUserUsecase: GetFirebaseCurrentUserUsecase,
    private val addFeedsUsecase: AddFeedsUsecase
) : ViewModel() {

    val isPhotoSelected = MutableStateFlow(false)
    val canTweet = MutableStateFlow(false)
    val currentUser: FirebaseUser?
        get() = getCurrentUserUsecase()

    private val _addFeed = MutableStateFlow<Resource<String>>(Resource.Nothing())
    val addFeed: StateFlow<Resource<String>> = _addFeed

    fun addFeed(feedCriteria: FeedCriteria) {
        _addFeed.value = Resource.Loading()
        viewModelScope.launch {
            addFeedsUsecase(feedCriteria).collectLatest {
                _addFeed.value = it
            }
        }
    }
}