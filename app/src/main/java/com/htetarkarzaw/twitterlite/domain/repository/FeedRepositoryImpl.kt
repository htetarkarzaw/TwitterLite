package com.htetarkarzaw.twitterlite.domain.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.htetarkarzaw.twitterlite.data.Resource
import com.htetarkarzaw.twitterlite.data.firebase.await
import com.htetarkarzaw.twitterlite.data.firebase.criteria.FeedCriteria
import com.htetarkarzaw.twitterlite.data.firebase.vo.FeedVO
import com.htetarkarzaw.twitterlite.data.firebase.vo.FeedVO.Companion.toFeedVO
import com.htetarkarzaw.twitterlite.data.local.TwitterLiteDatabase
import com.htetarkarzaw.twitterlite.data.local.entity.Feed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storageRef: StorageReference,
    private val firebaseAuth: FirebaseAuth,
    db: TwitterLiteDatabase
) : FeedRepository {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    private val dao = db.feedDao()

    override fun getFeeds(): Flow<Resource<String>> = flow {
        try {
            val feeds = firestore.collection("feeds").get().await()
            val feedList = arrayListOf<Feed>()
            Timber.tag("hakz.feed").d("${feeds.documents.count()}")
            for (feed in feeds) {
                Timber.tag("hakz.feed").d(feed.data.toString())
                feedList.add(feed.toFeedVO().toEntity())
            }
            insertFeeds(feedList)
            emit(Resource.Success("Success"))
        } catch (e: Exception) {
            Timber.tag("hakz.feeds.error").d(e.message ?: e.localizedMessage ?: "Something went wrong.")
            emit(Resource.Error(e.message ?: e.localizedMessage ?: "Something went wrong."))
        }
    }

    override fun addFeed(feedCriteria: FeedCriteria): Flow<Resource<String>> = flow {
        try {
            var photoUri: Uri? = null
            if (feedCriteria.photoUri != null) {
                val fileRef = storageRef.child("user/${currentUser!!.uid}/profile.jpg")
                fileRef.putFile(feedCriteria.photoUri).await()
                photoUri = fileRef.downloadUrl.await()
            }
            val document = firestore.collection("feeds").document()
            val feedVO = FeedVO(
                id = document.id,
                tweet = feedCriteria.tweet,
                photoUrl = photoUri?.toString(),
                date = feedCriteria.date,
                userId = currentUser!!.uid,
                userProfileUrl = currentUser!!.photoUrl?.toString(),
                userName = currentUser!!.displayName
            )
            document.set(feedVO).await()
            emit(Resource.Success("Successfully Added."))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: e.localizedMessage ?: "Something went wrong."))
        }
    }

    override fun deleteFeed(feedVO: FeedVO): Flow<Resource<String>> = flow {
        try {
            firestore.collection("feeds").document(feedVO.id).delete().await()
            emit(Resource.Success("Successfully Deleted."))
            deleteFeedFromDb(feedVO)
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: e.localizedMessage ?: "Something went wrong."))
        }
    }

    override suspend fun deleteFeedFromDb(feedVO: FeedVO) {
        withContext(Dispatchers.IO) {
            dao.deleteFeed(feedVO.toEntity())
        }
    }

    override suspend fun insertFeeds(feeds: List<Feed>) {
        withContext(Dispatchers.IO) {
            val dbFeeds = dao.retrievesFeeds()
            val itemToRemove = dbFeeds.filterNot { feeds.contains(it) }
            if (itemToRemove.isNotEmpty()) {
                dao.deleteFeeds(itemToRemove)
            }
            dao.insertFeeds(feeds)
        }
    }

    override fun retrieveFeeds(): Flow<List<Feed>> {
        return dao.retrievesFeedsViaFlow()
    }

    override fun getFeedById(feedId: String): Flow<Feed> {
        return dao.getFeedByIdViaFlow(feedId)
    }

}