package com.htetarkarzaw.twitterlite.ui.screen.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.htetarkarzaw.twitterlite.domain.usecase.DeleteAllFeedsUsecase
import com.htetarkarzaw.twitterlite.domain.usecase.GetFirebaseCurrentUserUsecase
import com.htetarkarzaw.twitterlite.domain.usecase.LogoutUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val logoutUsecase: LogoutUsecase,
    private val getCurrentUserUsecase: GetFirebaseCurrentUserUsecase,
    private val deleteAllFeedsUsecase: DeleteAllFeedsUsecase
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