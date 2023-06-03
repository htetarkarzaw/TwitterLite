package com.htetarkarzaw.twitterlite.domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.htetarkarzaw.twitterlite.data.Resource
import com.htetarkarzaw.twitterlite.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class registerWithEmailAndPasswordUsecase @Inject constructor(private val repo: AuthRepository) {
    suspend operator fun invoke(name: String,email: String, password: String): Flow<Resource<FirebaseUser>> {
        return repo.registerWithEmailAndPassword(name,email, password)
    }
}