package com.htetarkarzaw.twitterlite.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.htetarkarzaw.twitterlite.data.firebase.vo.FeedVO
import com.htetarkarzaw.twitterlite.utils.Constants
import java.io.Serializable

@Entity(tableName = Constants.TWITTER_LITE_TABLE_NAME)
data class Feed(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val tweet: String,
    val photoUrl: String?,
    val date: Long,
    val userId: String,
    val userProfileUrl: String?,
    val userName: String?
) : Serializable {
    fun toVO(): FeedVO {
        return FeedVO(
            id = id,
            tweet = tweet,
            photoUrl = photoUrl,
            date = date,
            userId = userId,
            userProfileUrl = userProfileUrl,
            userName = userName
        )
    }
}
