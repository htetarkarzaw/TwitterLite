package com.htetarkarzaw.twitterlite.ui.screen.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.htetarkarzaw.twitterlite.domain.usecase.deleteAllFeedsUsecase
import com.htetarkarzaw.twitterlite.domain.usecase.getFirebaseCurrentUserUsecase
import com.htetarkarzaw.twitterlite.domain.usecase.logoutUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val logoutUsecase: logoutUsecase,
    private val getCurrentUserUsecase: getFirebaseCurrentUserUsecase,
    private val deleteAllFeedsUsecase: deleteAllFeedsUsecase
) : ViewModel() {
    val currentUser: FirebaseUser?
        get() = getCurrentUserUsecase()

    fun logout() {
        if (currentUser != null) {
            logoutUsecase()
            viewModelScope.launch {
                deleteAllFeedsUsecase()
            }
        }
    }
}