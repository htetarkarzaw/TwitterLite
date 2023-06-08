package com.htetarkarzaw.twitterlite.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.htetarkarzaw.twitterlite.domain.repository.AuthRepository
import com.htetarkarzaw.twitterlite.domain.repository.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object FirebaseModule {
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideFirebaseStorageReference(): StorageReference = FirebaseStorage.getInstance().reference

    @Provides
    fun providesFirebaseFireStore(): FirebaseFirestore = FirebaseFirestore.getInstance()
}