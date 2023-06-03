package com.htetarkarzaw.twitterlite.domain.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.htetarkarzaw.twitterlite.data.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser : FirebaseUser?
    suspend fun loginWithEmailAndPassword(email: String, password: String): Flow<Resource<FirebaseUser>>
    suspend fun registerWithEmailAndPassword(name: String, email: String, password: String): Flow<Resource<FirebaseUser>>
    suspend fun updateUser(name: String, email: String, uri: Uri?): Flow<Resource<FirebaseUser>>
    fun logout()
}