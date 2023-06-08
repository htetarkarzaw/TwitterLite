package com.htetarkarzaw.twitterlite.domain.usecase

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.htetarkarzaw.twitterlite.data.Resource
import com.htetarkarzaw.twitterlite.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateUserUsecase @Inject constructor(private val repo: AuthRepository) {
    suspend operator fun invoke(name: String, email: String, uri: Uri?): Flow<Resource<FirebaseUser>> {
        return repo.updateUser(name,email, uri)
    }
}