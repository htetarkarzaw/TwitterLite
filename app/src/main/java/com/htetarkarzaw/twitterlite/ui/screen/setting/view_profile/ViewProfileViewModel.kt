package com.htetarkarzaw.twitterlite.ui.screen.setting.view_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.htetarkarzaw.twitterlite.data.firebase.vo.FeedVO
import com.htetarkarzaw.twitterlite.domain.usecase.GetFirebaseCurrentUserUsecase
import com.htetarkarzaw.twitterlite.domain.usecase.RetrieveFeedsByUserIdUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewProfileViewModel @Inject constructor(
    private val getCurrentUserUsecase: GetFirebaseCurrentUserUsecase,
    private val retrieveFeedsByUserIdUsecase: RetrieveFeedsByUserIdUsecase
) : ViewModel() {
    val currentUser: FirebaseUser?
        get() = getCurrentUserUsecase()

    private val _feeds = MutableStateFlow<List<FeedVO>>(emptyList())
    val feeds = _feeds.asStateFlow()

    init {
        getFeedsByUserId()
    }

    private fun getFeedsByUserId() {
        viewModelScope.launch {
            currentUser?.let {
                retrieveFeedsByUserIdUsecase(it.uid).collectLatest { list ->
                    _feeds.value = list
                }
            }
        }
    }
}