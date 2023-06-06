package com.htetarkarzaw.twitterlite.data.firebase.vo

import com.google.firebase.firestore.DocumentSnapshot
import com.htetarkarzaw.twitterlite.data.local.entity.Feed
import java.io.Serializable

data class FeedVO(
    val id: String,
    val tweet: String,
    val photoUrl: String?,
    val date: Long,
    val userId: String,
    val userProfileUrl: String?,
    val userName: String?
) : Serializable {
    fun toEntity(): Feed {
        return Feed(
            id = id,
            tweet = tweet,
            photoUrl = photoUrl,
            date = date,
            userId = userId,
            userProfileUrl = userProfileUrl,
            userName = userName
        )
    }

    companion object {
        fun DocumentSnapshot.toFeedVO(): FeedVO {
            return try {
                val id = id
                val photoUrl = getString("photoUrl")
                val tweet = getString("tweet")!!
                val date = getLong("date")!!
                val userId = getString("userId")!!
                val userProfileUrl = getString("userProfileUrl")
                val userName = getString("userName")
                FeedVO(id, tweet, photoUrl, date, userId, userProfileUrl, userName)
            } catch (e: Exception) {
                throw Exception("Error converting user profile")
            }
        }

        private const val TAG = "Feed"
    }
}
