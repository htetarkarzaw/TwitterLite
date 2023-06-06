package com.htetarkarzaw.twitterlite.domain.vo

import com.google.firebase.firestore.DocumentSnapshot
import com.htetarkarzaw.twitterlite.data.firebase.await
import java.io.Serializable

data class FeedVO(
    val id: String,
    val tweet: String,
    val photoUrl: String?,
    val date: Long,
    val user: Map<*, *>,
) : Serializable {
    companion object {
        fun DocumentSnapshot.toFeedVO(): FeedVO {
            return try {
                val id = id
                val photoUrl = getString("photoUrl")
                val tweet = getString("tweet")!!
                val date = getLong("date")!!
                val user = get("user") as HashMap<*, *>
                FeedVO(id, tweet, photoUrl, date, user)
            } catch (e: Exception) {
                throw Exception("Error converting user profile")
            }
        }

        private const val TAG = "Feed"
    }
}
