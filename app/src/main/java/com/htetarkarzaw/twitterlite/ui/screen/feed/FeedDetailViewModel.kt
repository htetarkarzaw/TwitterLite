package com.htetarkarzaw.twitterlite.ui.screen.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.htetarkarzaw.twitterlite.data.Resource
import com.htetarkarzaw.twitterlite.data.firebase.vo.FeedVO
import com.htetarkarzaw.twitterlite.domain.usecase.DeleteFeedsUsecase
import com.htetarkarzaw.twitterlite.domain.usecase.GetFirebaseCurrentUserUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedDetailViewModel @Inject constructor(
    private val deleteFeedsUsecase: DeleteFeedsUsecase,
    private val getCurrentUserUsecase: GetFirebaseCurrentUserUsecase
) : ViewModel() {

    private val _deleteFeed = MutableStateFlow<Resource<String>>(Resource.Nothing())
    val deleteFeed: MutableStateFlow<Resource<String>> = _deleteFeed

    val currentUser: FirebaseUser?
        get() = getCurrentUserUsecase()

    fun deleteFeedById(feedVO: FeedVO) {
        _deleteFeed.value = Resource.Loading()
        viewModelScope.launch {
            deleteFeedsUsecase(feedVO).collectLatest {
                _deleteFeed.value = it
            }
        }
    }

}