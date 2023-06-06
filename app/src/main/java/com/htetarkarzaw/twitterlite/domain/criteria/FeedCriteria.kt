package com.htetarkarzaw.twitterlite.domain.criteria

import android.net.Uri
import java.io.Serializable

data class FeedCriteria(
    val tweet: String,
    val photoUri: Uri?,
    val date: Long
) : Serializable

