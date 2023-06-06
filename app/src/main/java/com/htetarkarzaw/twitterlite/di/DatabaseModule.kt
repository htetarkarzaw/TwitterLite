package com.htetarkarzaw.twitterlite.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.htetarkarzaw.twitterlite.data.local.TwitterLiteDatabase
import com.htetarkarzaw.twitterlite.domain.repository.AuthRepository
import com.htetarkarzaw.twitterlite.domain.repository.AuthRepositoryImpl
import com.htetarkarzaw.twitterlite.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context):TwitterLiteDatabase{
        return Room.databaseBuilder(context,TwitterLiteDatabase::class.java, Constants.TWITTER_LITE_DATABASE)
            .fallbackToDestructiveMigration()
            .build()
    }
}