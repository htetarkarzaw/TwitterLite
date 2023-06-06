package com.htetarkarzaw.twitterlite.domain.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.htetarkarzaw.twitterlite.data.Resource
import com.htetarkarzaw.twitterlite.data.firebase.await
import com.htetarkarzaw.twitterlite.domain.criteria.FeedCriteria
import com.htetarkarzaw.twitterlite.domain.vo.FeedVO
import com.htetarkarzaw.twitterlite.domain.vo.FeedVO.Companion.toFeedVO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storageRef: StorageReference,
    private val firebaseAuth: FirebaseAuth,
) : FeedRepository {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override fun getFeeds(): Flow<Resource<List<FeedVO>>> = flow {
        try {
            val feeds = firestore.collection("feeds").get().await()
            val feedList = arrayListOf<FeedVO>()
            Timber.tag("hakz.feed").d("${feeds.documents.count()}")
            for (feed in feeds) {
                Timber.tag("hakz.feed").d(feed.data.toString())
                feedList.add(feed.toFeedVO())
            }
            emit(Resource.Success(feedList))
        } catch (e: Exception) {
            Timber.tag("hakz.feeds.error").d(e.message ?: e.localizedMessage ?: "Something went wrong.")
            emit(Resource.Error(e.message ?: e.localizedMessage ?: "Something went wrong."))
        }
    }

    override fun addFeeds(feedCriteria: FeedCriteria): Flow<Resource<String>> = flow {
        try {
            var photoUri: Uri? = null
            if (feedCriteria.photoUri != null) {
                val fileRef = storageRef.child("user/${currentUser!!.uid}/profile.jpg")
                fileRef.putFile(feedCriteria.photoUri).await()
                photoUri = fileRef.downloadUrl.await()
            }
            val user = HashMap<String,String>()
            currentUser!!.displayName?.let { user["name"] =it }
            currentUser!!.photoUrl?.let { user["profileUrl"] =it.toString() }
            currentUser!!.uid?.let { user["userId"] =it }
            val document = firestore.collection("feeds").document()
            val feedVO = FeedVO(
                id = document.id,
                tweet = feedCriteria.tweet,
                photoUrl = photoUri.toString(),
                date = feedCriteria.date,
                user = user
            )
            document.set(feedVO).await()
            emit(Resource.Success("success"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: e.localizedMessage ?: "Something went wrong."))
        }
    }

}