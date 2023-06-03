package com.htetarkarzaw.twitterlite.domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.htetarkarzaw.twitterlite.domain.repository.AuthRepository
import javax.inject.Inject

class getFirebaseCurrentUserUsecase @Inject constructor(private val repo: AuthRepository) {
    operator fun invoke(): FirebaseUser? {
        return repo.currentUser
    }
}