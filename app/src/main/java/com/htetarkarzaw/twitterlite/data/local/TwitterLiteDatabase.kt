package com.htetarkarzaw.twitterlite.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.htetarkarzaw.twitterlite.data.local.dao.FeedDao
import com.htetarkarzaw.twitterlite.data.local.entity.Feed

@Database(entities = [Feed::class], version = 1)
abstract class TwitterLiteDatabase : RoomDatabase() {
    abstract fun feedDao(): FeedDao
}