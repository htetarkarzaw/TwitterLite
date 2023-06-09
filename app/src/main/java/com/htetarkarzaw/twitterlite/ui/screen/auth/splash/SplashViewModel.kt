package com.htetarkarzaw.twitterlite.ui.screen.auth.splash

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.htetarkarzaw.twitterlite.domain.usecase.GetFirebaseCurrentUserUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val getCurrentUserUsecase: GetFirebaseCurrentUserUsecase) : ViewModel() {
    val currentUser: FirebaseUser?
        get() = getCurrentUserUsecase()
}