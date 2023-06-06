package com.htetarkarzaw.twitterlite.domain.vo

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ServerTimestamp
import timber.log.Timber
import java.io.Serializable
import java.util.Date

data class UserVO(
    val id: String,
    val photoUrl: String?,
    val name : String,
    val email : String,
) : Serializable {
    companion object {
        fun DocumentSnapshot?.toUserVO(): UserVO ?{
            return try {
                val id = this!!.id
                val photoUrl = getString("photoUrl")
                val name = getString("name")!!
                val email = getString("email")!!
                UserVO(id, photoUrl, name, email)
            } catch (e: Exception) {
               null
            }
        }

        private const val TAG = "Feed"
    }
}
