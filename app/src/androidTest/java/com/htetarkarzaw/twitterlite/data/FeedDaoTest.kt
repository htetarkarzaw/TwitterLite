package com.htetarkarzaw.twitterlite.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.htetarkarzaw.twitterlite.data.local.TwitterLiteDatabase
import com.htetarkarzaw.twitterlite.data.local.dao.FeedDao
import com.htetarkarzaw.twitterlite.data.local.entity.Feed
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FeedDaoTest {
    private lateinit var database: TwitterLiteDatabase
    private lateinit var feedDao: FeedDao

    @Before
    fun setup() {
        database = Room
            .inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                TwitterLiteDatabase::class.java,
            )
            .allowMainThreadQueries()
            .build()
        feedDao = database.feedDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun successfully_insert_and_retrieve_feeds() = runBlocking {
        val feeds = listOf(
            Feed(
                id = "1",
                tweet = "This is first one.",
                photoUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=f4c5660e-0331-4260-8e3c-f844f355cc39",
                date = 1686054321070,
                userId = "1",
                userProfileUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=90abdf8d-fe26-4f04-929f-dc9168b17710",
                userName = "The One"
            ),
            Feed(
                id = "2",
                tweet = "This is second one.",
                photoUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=f4c5660e-0331-4260-8e3c-f844f355cc39",
                date = 1686054321070,
                userId = "2",
                userProfileUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=90abdf8d-fe26-4f04-929f-dc9168b17710",
                userName = "The Two"
            ),
            Feed(
                id = "3",
                tweet = "This is third one.",
                photoUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=f4c5660e-0331-4260-8e3c-f844f355cc39",
                date = 1686054321070,
                userId = "3",
                userProfileUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=90abdf8d-fe26-4f04-929f-dc9168b17710",
                userName = "The Three"
            ),
            Feed(
                id = "4",
                tweet = "This is fourth one.",
                photoUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=f4c5660e-0331-4260-8e3c-f844f355cc39",
                date = 1686054321070,
                userId = "4",
                userProfileUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=90abdf8d-fe26-4f04-929f-dc9168b17710",
                userName = "The four"
            ),
            Feed(
                id = "5",
                tweet = "This is fifth one.",
                photoUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=f4c5660e-0331-4260-8e3c-f844f355cc39",
                date = 1686054321070,
                userId = "5",
                userProfileUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=90abdf8d-fe26-4f04-929f-dc9168b17710",
                userName = "The five"
            )
        )
        feedDao.insertFeeds(feeds)
        val dbCars = feedDao.retrievesFeedsViaFlow()
        assertEquals(feeds.size, dbCars.first().size)
    }

    @Test
    fun successfully_deleted_all_feeds() = runBlocking {
        val feeds = listOf(
            Feed(
                id = "1",
                tweet = "This is first one.",
                photoUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=f4c5660e-0331-4260-8e3c-f844f355cc39",
                date = 1686054321070,
                userId = "1",
                userProfileUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=90abdf8d-fe26-4f04-929f-dc9168b17710",
                userName = "The One"
            ),
            Feed(
                id = "2",
                tweet = "This is second one.",
                photoUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=f4c5660e-0331-4260-8e3c-f844f355cc39",
                date = 1686054321070,
                userId = "2",
                userProfileUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=90abdf8d-fe26-4f04-929f-dc9168b17710",
                userName = "The Two"
            ),
            Feed(
                id = "3",
                tweet = "This is third one.",
                photoUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=f4c5660e-0331-4260-8e3c-f844f355cc39",
                date = 1686054321070,
                userId = "3",
                userProfileUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=90abdf8d-fe26-4f04-929f-dc9168b17710",
                userName = "The Three"
            ),
            Feed(
                id = "4",
                tweet = "This is fourth one.",
                photoUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=f4c5660e-0331-4260-8e3c-f844f355cc39",
                date = 1686054321070,
                userId = "4",
                userProfileUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=90abdf8d-fe26-4f04-929f-dc9168b17710",
                userName = "The four"
            ),
            Feed(
                id = "5",
                tweet = "This is fifth one.",
                photoUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=f4c5660e-0331-4260-8e3c-f844f355cc39",
                date = 1686054321070,
                userId = "5",
                userProfileUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=90abdf8d-fe26-4f04-929f-dc9168b17710",
                userName = "The five"
            )
        )
        feedDao.insertFeeds(feeds)
        feedDao.deleteAllFeeds()
        val dbCars = feedDao.retrievesFeedsViaFlow()
        assertEquals(0, dbCars.first().size)
    }

    @Test
    fun successfully_insert_and_retrieve_feed_by_userId() = runBlocking {
        val feeds = listOf(
            Feed(
                id = "1",
                tweet = "This is first one.",
                photoUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=f4c5660e-0331-4260-8e3c-f844f355cc39",
                date = 1686054321070,
                userId = "1",
                userProfileUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=90abdf8d-fe26-4f04-929f-dc9168b17710",
                userName = "The One"
            ),
            Feed(
                id = "2",
                tweet = "This is second one.",
                photoUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=f4c5660e-0331-4260-8e3c-f844f355cc39",
                date = 1686054321070,
                userId = "1",
                userProfileUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=90abdf8d-fe26-4f04-929f-dc9168b17710",
                userName = "The Two"
            ),
            Feed(
                id = "3",
                tweet = "This is third one.",
                photoUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=f4c5660e-0331-4260-8e3c-f844f355cc39",
                date = 1686054321070,
                userId = "1",
                userProfileUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=90abdf8d-fe26-4f04-929f-dc9168b17710",
                userName = "The Three"
            ),
            Feed(
                id = "4",
                tweet = "This is fourth one.",
                photoUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=f4c5660e-0331-4260-8e3c-f844f355cc39",
                date = 1686054321070,
                userId = "4",
                userProfileUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=90abdf8d-fe26-4f04-929f-dc9168b17710",
                userName = "The four"
            ),
            Feed(
                id = "5",
                tweet = "This is fifth one.",
                photoUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=f4c5660e-0331-4260-8e3c-f844f355cc39",
                date = 1686054321070,
                userId = "5",
                userProfileUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=90abdf8d-fe26-4f04-929f-dc9168b17710",
                userName = "The five"
            )
        )
        feedDao.insertFeeds(feeds)
        val feed = feedDao.retrieveFeedsByUserId("1").first()
        assertEquals(3,feed.size)
    }

    @Test
    fun successfully_insert_and_delete_feed() = runBlocking {
        val feeds = listOf(
            Feed(
                id = "1",
                tweet = "This is first one.",
                photoUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=f4c5660e-0331-4260-8e3c-f844f355cc39",
                date = 1686054321070,
                userId = "1",
                userProfileUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=90abdf8d-fe26-4f04-929f-dc9168b17710",
                userName = "The One"
            ),
            Feed(
                id = "2",
                tweet = "This is second one.",
                photoUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=f4c5660e-0331-4260-8e3c-f844f355cc39",
                date = 1686054321070,
                userId = "1",
                userProfileUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=90abdf8d-fe26-4f04-929f-dc9168b17710",
                userName = "The Two"
            ),
            Feed(
                id = "3",
                tweet = "This is third one.",
                photoUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=f4c5660e-0331-4260-8e3c-f844f355cc39",
                date = 1686054321070,
                userId = "1",
                userProfileUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=90abdf8d-fe26-4f04-929f-dc9168b17710",
                userName = "The Three"
            ),
            Feed(
                id = "4",
                tweet = "This is fourth one.",
                photoUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=f4c5660e-0331-4260-8e3c-f844f355cc39",
                date = 1686054321070,
                userId = "4",
                userProfileUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=90abdf8d-fe26-4f04-929f-dc9168b17710",
                userName = "The four"
            ),
            Feed(
                id = "5",
                tweet = "This is fifth one.",
                photoUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=f4c5660e-0331-4260-8e3c-f844f355cc39",
                date = 1686054321070,
                userId = "5",
                userProfileUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=90abdf8d-fe26-4f04-929f-dc9168b17710",
                userName = "The five"
            )
        )
        val deleteFeed = Feed(
            id = "5",
            tweet = "This is fifth one.",
            photoUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=f4c5660e-0331-4260-8e3c-f844f355cc39",
            date = 1686054321070,
            userId = "5",
            userProfileUrl = "https://firebasestorage.googleapis.com/v0/b/twitterlite-b25c2.appspot.com/o/user%2FyHhov1hG6vVNgLyF0yVuZlIOCGb2%2Fprofile.jpg?alt=media&token=90abdf8d-fe26-4f04-929f-dc9168b17710",
            userName = "The five"
        )
        feedDao.insertFeeds(feeds)
        val feedBeforeDelete = feedDao.getFeedByIdViaFlow("5").first()
        assertEquals("before_delete",deleteFeed,feedBeforeDelete)
        feedDao.deleteFeed(deleteFeed)
        val feedAfterDelete = feedDao.getFeedByIdViaFlow("5").first()
        assertEquals("before_delete",null,feedAfterDelete)
    }


}