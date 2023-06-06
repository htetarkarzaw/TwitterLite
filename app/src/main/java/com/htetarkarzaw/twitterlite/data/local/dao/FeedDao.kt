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

    @Query("SELECT * FROM ${Constants.TWITTER_LITE_TABLE_NAME} WHERE id == :id")
    suspend fun getFeedById(id: String): Feed?

    @Query("SELECT * FROM ${Constants.TWITTER_LITE_TABLE_NAME} WHERE id == :id")
    fun getFeedByIdViaFlow(id: String): Flow<Feed>

    @Delete
    fun deleteFeed(feed: Feed)

    @Delete
    fun deleteFeeds(list: List<Feed>)

    @Update
    suspend fun updateFeed(feed: Feed): Int
}