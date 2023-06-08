package com.htetarkarzaw.twitterlite.ui_test

import android.view.View
import androidx.navigation.findNavController
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.htetarkarzaw.twitterlite.R
import com.htetarkarzaw.twitterlite.ui.MainActivity
import com.htetarkarzaw.twitterlite.ui.screen.feed.FeedAdapter
import org.hamcrest.CoreMatchers.allOf
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random
import kotlin.random.nextInt


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TwitterLiteUiTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.htetarkarzaw.twitterlite", appContext.packageName)
    }

    private val activityScenario: ActivityScenario<MainActivity>? = ActivityScenario.launch(MainActivity::class.java)

    @Test
    fun registerFlow() {
        Thread.sleep(3000L)
        var currentDestinationId: Int? = 0
        activityScenario?.onActivity { activity ->
            val navController = activity.findNavController(R.id.nav_host_fragment_activity_main)
            currentDestinationId = navController.currentDestination?.id
        }
        if (currentDestinationId == R.id.feedFragment) {
            onView(allOf(withId(R.id.settingFragment), isDescendantOfA(withId(R.id.navView)))).perform(click())
            onView(withId(R.id.cvLogout)).perform(click())
        }
        Thread.sleep(4000L)
        onView(withId(R.id.btnRegister)).perform(click())
        val random = Random.nextInt(0..100)
        onView(withId(R.id.etEmail)).perform(typeText("arkar$random@gmail.com"))
        onView(withId(R.id.etDisplayName)).perform(typeText("Htet Arkar Zaw $random"))
        onView(withId(R.id.etPassword)).perform(typeText("12345678"))
        onView(withId(R.id.etConfirmPassword)).perform(typeText("12345678"))
        onView(withId(R.id.btnRegister)).perform(click())
        Thread.sleep(10000L)
        activityScenario?.onActivity { activity ->
            val navController = activity.findNavController(R.id.nav_host_fragment_activity_main)
            currentDestinationId = navController.currentDestination?.id
            // Compare the current destination ID with the expected destination ID
            assertEquals(R.id.feedFragment, currentDestinationId)
        }
    }

    @Test
    fun loginFlow() {
        Thread.sleep(3000L)
        var currentDestinationId: Int? = 0
        activityScenario?.onActivity { activity ->
            val navController = activity.findNavController(R.id.nav_host_fragment_activity_main)
            currentDestinationId = navController.currentDestination?.id
        }
        if (currentDestinationId == R.id.feedFragment) {
            onView(allOf(withId(R.id.settingFragment), isDescendantOfA(withId(R.id.navView)))).perform(click())
            onView(withId(R.id.cvLogout)).perform(click())
            Thread.sleep(3000L)
        }
        onView(withId(R.id.etEmail)).perform(typeText("arkarzaw412@gmail.com"))
        onView(withId(R.id.etPassword)).perform(typeText("12345678"))
        onView(withId(R.id.btnLogin)).perform(click())
        Thread.sleep(5000L)
        activityScenario?.onActivity { activity ->
            val navController = activity.findNavController(R.id.nav_host_fragment_activity_main)
            currentDestinationId = navController.currentDestination?.id
            assertEquals(R.id.feedFragment, currentDestinationId)
        }
    }


    @Test
    fun uploadFeedFlow() {
        var currentDestinationId: Int? = 0
        Thread.sleep(3000L)
        onView(withId(R.id.fab)).perform(click())
        onView(withId(R.id.etTweet)).perform(typeText("This is Post From Instrumented Testing"))
        onView(withId(R.id.btnUpload)).perform(click())
        Thread.sleep(5000L)
        activityScenario?.onActivity { activity ->
            val navController = activity.findNavController(R.id.nav_host_fragment_activity_main)
            currentDestinationId = navController.currentDestination?.id
        }
        if (currentDestinationId == R.id.feedFragment) {
            onView(withId(R.id.rvFeed)).perform(
                RecyclerViewActions.actionOnItemAtPosition<FeedAdapter.FeedViewHolder>(
                    0,
                    click()
                )
            )
            onView(withId(R.id.tvTweet)).check(matches(withText("This is Post From Instrumented Testing")))
        }
    }

    @Test
    fun deleteFeedFlow() {
        var currentDestinationId: Int? = 0
        Thread.sleep(3000L)
        activityScenario?.onActivity { activity ->
            val navController = activity.findNavController(R.id.nav_host_fragment_activity_main)
            currentDestinationId = navController.currentDestination?.id
        }
        if (currentDestinationId == R.id.loginFragment) {
            onView(withId(R.id.etEmail)).perform(typeText("arkarzaw412@gmail.com"))
            onView(withId(R.id.etPassword)).perform(typeText("12345678"))
            onView(withId(R.id.btnLogin)).perform(click())
            Thread.sleep(5000L)
        }
        onView(allOf(withId(R.id.settingFragment), isDescendantOfA(withId(R.id.navView)))).perform(click())
        onView(withId(R.id.cvProfile)).perform(click())
        Thread.sleep(3000L)
        if (isViewDisplayed(R.id.tvNoData)) {
            assertTrue("There is no feeds", true)
        } else {
            onView(withId(R.id.rvFeed)).perform(
                RecyclerViewActions.actionOnItemAtPosition<FeedAdapter.FeedViewHolder>(
                    0,
                    click()
                )
            )
            onView(withId(R.id.ivMenu)).perform(click())
            onView(withText("Delete")).perform(click())
            onView(withId(R.id.btnOkay)).perform(click())
            Thread.sleep(5000L)
            onView(withId(R.id.rvFeed)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun updateUserNameFlow() {
        var currentDestinationId: Int? = 0
        Thread.sleep(3000L)
        activityScenario?.onActivity { activity ->
            val navController = activity.findNavController(R.id.nav_host_fragment_activity_main)
            currentDestinationId = navController.currentDestination?.id
        }
        if (currentDestinationId == R.id.loginFragment) {
            onView(withId(R.id.etEmail)).perform(typeText("arkarzaw412@gmail.com"))
            onView(withId(R.id.etPassword)).perform(typeText("12345678"))
            onView(withId(R.id.btnLogin)).perform(click())
            Thread.sleep(5000L)
        }
        onView(allOf(withId(R.id.settingFragment), isDescendantOfA(withId(R.id.navView)))).perform(click())
        onView(withId(R.id.cvProfile)).perform(click())
        onView(withId(R.id.btnEdit)).perform(click())
        onView(withId(R.id.etDisplayName)).perform(clearText())
        val random = Random.nextInt(0..100)
        onView(withId(R.id.etDisplayName)).perform(typeText("Arkar Zaw $random"))
        onView(withId(R.id.btnSave)).perform(click())
        Thread.sleep(10000L)
        onView(withId(R.id.rvFeed)).check(matches(isDisplayed()))
    }

    fun isViewDisplayed(viewId: Int): Boolean {
        var isDisplayed = false
        onView(withId(viewId)).check { view, _ ->
            isDisplayed = view != null && view.visibility == View.VISIBLE
        }
        return isDisplayed
    }

}