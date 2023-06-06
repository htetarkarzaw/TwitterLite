package com.htetarkarzaw.twitterlite.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.htetarkarzaw.twitterlite.data.Resource
import com.htetarkarzaw.twitterlite.domain.criteria.FeedCriteria
import com.htetarkarzaw.twitterlite.domain.vo.FeedVO
import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    fun getFeeds(): Flow<Resource<List<FeedVO>>>
    fun addFeeds(feedCriteria: FeedCriteria): Flow<Resource<String>>
    val currentUser: FirebaseUser?
}