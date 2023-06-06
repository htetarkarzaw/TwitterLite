package com.htetarkarzaw.twitterlite.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.htetarkarzaw.twitterlite.domain.repository.AuthRepository
import com.htetarkarzaw.twitterlite.domain.repository.AuthRepositoryImpl
import com.htetarkarzaw.twitterlite.domain.repository.FeedRepository
import com.htetarkarzaw.twitterlite.domain.repository.FeedRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    fun providesAuthRepository(repo: AuthRepositoryImpl): AuthRepository = repo

    @Provides
    fun providesFeedRepository(repo: FeedRepositoryImpl): FeedRepository = repo
}