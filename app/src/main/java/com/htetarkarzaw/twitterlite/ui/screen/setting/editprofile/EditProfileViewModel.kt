package com.htetarkarzaw.twitterlite.ui.screen.setting.editprofile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.htetarkarzaw.twitterlite.data.Resource
import com.htetarkarzaw.twitterlite.domain.usecase.GetFirebaseCurrentUserUsecase
import com.htetarkarzaw.twitterlite.domain.usecase.UpdateUserUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val getCurrentUserUsecase: GetFirebaseCurrentUserUsecase,
    private val updateUserUsecase: UpdateUserUsecase
) : ViewModel() {
    val currentUser: FirebaseUser?
        get() = getCurrentUserUsecase()

    private val _chooser = MutableSharedFlow<String>()
    val chooser: SharedFlow<String> = _chooser

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

    fun updateChooser(chooser: String){
        viewModelScope.launch {
            _chooser.emit(chooser)
        }
    }
}