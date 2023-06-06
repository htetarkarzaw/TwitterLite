package com.htetarkarzaw.twitterlite.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.htetarkarzaw.twitterlite.data.Resource
import com.htetarkarzaw.twitterlite.data.firebase.criteria.FeedCriteria
import com.htetarkarzaw.twitterlite.data.firebase.vo.FeedVO
import com.htetarkarzaw.twitterlite.data.local.entity.Feed
import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    fun getFeeds(): Flow<Resource<String>>
    fun addFeed(feedCriteria: FeedCriteria): Flow<Resource<String>>
    fun deleteFeed(feedVO: FeedVO) : Flow<Resource<String>>
    suspend fun insertFeeds(feeds: List<Feed>)
    fun retrieveFeeds(): Flow<List<Feed>>
    fun getFeedById(feedId: String): Flow<Feed>
    suspend fun deleteFeedFromDb(feedVO: FeedVO)
    val currentUser: FirebaseUser?
}