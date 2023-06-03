package com.htetarkarzaw.twitterlite.ui.screen.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.htetarkarzaw.twitterlite.data.Resource
import com.htetarkarzaw.twitterlite.domain.usecase.loginWithEmailAndPasswordUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginWithEmailAndPasswordUsecase: loginWithEmailAndPasswordUsecase) :
    ViewModel() {
    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>>(Resource.Nothing())
    val loginFlow: StateFlow<Resource<FirebaseUser>> = _loginFlow

    fun loginWithEmailAndPassword(email: String, password: String) {
        _loginFlow.value = Resource.Loading()
        viewModelScope.launch {
            loginWithEmailAndPasswordUsecase(email, password).collectLatest {
                _loginFlow.value = it
            }
        }
    }
}