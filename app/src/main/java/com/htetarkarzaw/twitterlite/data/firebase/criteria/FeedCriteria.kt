package com.htetarkarzaw.twitterlite.data.firebase.criteria

import android.net.Uri
import java.io.Serializable

data class FeedCriteria(
    val tweet: String,
    val photoUri: Uri?,
    val date: Long
) : Serializable

