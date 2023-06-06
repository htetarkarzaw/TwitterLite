package com.htetarkarzaw.twitterlite.domain.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.htetarkarzaw.twitterlite.data.Resource
import com.htetarkarzaw.twitterlite.data.firebase.await
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val storageRef: StorageReference
) : AuthRepository {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun loginWithEmailAndPassword(email: String, password: String): Flow<Resource<FirebaseUser>> =
        flow {
            try {
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                emit(Resource.Success(result.user!!))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: e.localizedMessage ?: "Something went wrong."))
            }
        }

    override suspend fun registerWithEmailAndPassword(
        name: String,
        email: String,
        password: String
    ): Flow<Resource<FirebaseUser>> =
        flow {
            try {
                val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                result.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())?.await()
                emit(Resource.Success(result.user!!))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: e.localizedMessage ?: "Something went wrong."))
            }
        }

    override suspend fun updateUser(name: String, email: String, uri: Uri?): Flow<Resource<FirebaseUser>> = flow {
        var profileUrl = uri
        try {
            if (currentUser != null) {
                currentUser!!.updateEmail(email).await()
                val profileUpdates: UserProfileChangeRequest
                if(profileUrl!=null){
                    val fileRef = storageRef.child("user/${currentUser!!.uid}/profile.jpg")
                    fileRef.putFile(profileUrl).await()
                    profileUrl = fileRef.downloadUrl.await()
                    profileUpdates = userProfileChangeRequest {
                        displayName = name
                        photoUri = profileUrl
                    }
                }else{
                    profileUpdates = userProfileChangeRequest {
                        displayName = name
                    }
                }
                currentUser!!.updateProfile(profileUpdates).await()
                emit(Resource.Success(currentUser!!))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: e.localizedMessage ?: "Something went wrong."))
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}