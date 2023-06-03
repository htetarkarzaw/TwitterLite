package com.htetarkarzaw.twitterlite.ui.screen.setting.editprofile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.htetarkarzaw.twitterlite.data.Resource
import com.htetarkarzaw.twitterlite.domain.usecase.getFirebaseCurrentUserUsecase
import com.htetarkarzaw.twitterlite.domain.usecase.logoutUsecase
import com.htetarkarzaw.twitterlite.domain.usecase.updateUserUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val logoutUsecase: logoutUsecase,
    private val getCurrentUserUsecase: getFirebaseCurrentUserUsecase,
    private val updateUserUsecase: updateUserUsecase
) : ViewModel() {
    val currentUser: FirebaseUser?
        get() = getCurrentUserUsecase()

    private val _updateFlow = MutableStateFlow<Resource<FirebaseUser>>(Resource.Nothing())
    val updateFlow: StateFlow<Resource<FirebaseUser>> = _updateFlow

    fun updateUser(name: String, email: String, uri: Uri?) {
        _updateFlow.value = Resource.Loading()
        viewModelScope.launch {
            updateUserUsecase(name, email, uri).collectLatest {
                _updateFlow.value = it
            }
        }
    }
}