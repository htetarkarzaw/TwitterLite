package com.htetarkarzaw.twitterlite.data.local.dao

import androidx.room.*
import com.htetarkarzaw.twitterlite.data.local.entity.Feed
import com.htetarkarzaw.twitterlite.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface FeedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeeds(movies: List<Feed>)

    @Query("SELECT * FROM ${Constants.TWITTER_LITE_TABLE_NAME}")
    fun retrievesFeedsViaFlow(): Flow<List<Feed>>

    @Query("SELECT * FROM ${Constants.TWITTER_LITE_TABLE_NAME}")
    fun retrievesFeeds(): List<Feed>

    @Query("SELECT * FROM ${Constants.TWITTER_LITE_TABLE_NAME} WHERE userId == :id")
    fun retrieveFeedsByUserId(id: String): Flow<List<Feed>>

    @Query("SELECT * FROM ${Constants.TWITTER_LITE_TABLE_NAME} WHERE id == :id")
    fun getFeedByIdViaFlow(id: String): Flow<Feed>

    @Delete
    suspend fun deleteFeed(feed: Feed)

    @Delete
    suspend fun deleteFeeds(list: List<Feed>)

    @Query("DELETE FROM ${Constants.TWITTER_LITE_TABLE_NAME}")
    suspend fun deleteAllFeeds()

    @Update
    suspend fun updateFeed(feed: Feed): Int
}