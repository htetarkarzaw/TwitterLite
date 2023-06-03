package com.htetarkarzaw.twitterlite.ui.screen.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.htetarkarzaw.twitterlite.data.Resource
import com.htetarkarzaw.twitterlite.domain.usecase.registerWithEmailAndPasswordUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerWithEmailAndPasswordUsecase: registerWithEmailAndPasswordUsecase) :
    ViewModel() {
    private val _registerFlow = MutableStateFlow<Resource<FirebaseUser>>(Resource.Nothing())
    val registerFlow: StateFlow<Resource<FirebaseUser>> = _registerFlow

    fun registerWithEmailAndPassword(name: String, email: String, password: String) {
        _registerFlow.value = Resource.Loading()
        viewModelScope.launch {
            registerWithEmailAndPasswordUsecase(name, email, password).collectLatest {
                _registerFlow.value = it
            }
        }
    }
}