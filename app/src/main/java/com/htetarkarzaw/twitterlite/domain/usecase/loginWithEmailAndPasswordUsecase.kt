package com.htetarkarzaw.twitterlite.domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.htetarkarzaw.twitterlite.data.Resource
import com.htetarkarzaw.twitterlite.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class loginWithEmailAndPasswordUsecase @Inject constructor(private val repo: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<FirebaseUser>> {
        return repo.loginWithEmailAndPassword(email, password)
    }
}