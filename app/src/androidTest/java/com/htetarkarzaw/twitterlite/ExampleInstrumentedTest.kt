package com.htetarkarzaw.twitterlite

import android.view.View
import androidx.annotation.IdRes
import androidx.navigation.findNavController
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.htetarkarzaw.twitterlite.ui.MainActivity
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.htetarkarzaw.twitterlite", appContext.packageName)
    }


//    @JvmField
//
//    var activityScenarioRule: ActivityScenarioRule<MainActivity?>? = ActivityScenarioRule(MainActivity::class.java)

    private val activityScenario: ActivityScenario<MainActivity>? = ActivityScenario.launch(MainActivity::class.java)


    @Test
    fun loginFlow() {
        Thread.sleep(3000L)
        var currentDestinationId : Int? = 0
        activityScenario?.onActivity { activity ->
            val navController = activity.findNavController(R.id.nav_host_fragment_activity_main)
            currentDestinationId = navController.currentDestination?.id
        }
        if(currentDestinationId == R.id.feedFragment){
//            onView(allOf(withId(R.id.navView),ViewMatchers.hasSibling(withId(R.id.feedFragment)))).perform(click())
            onView(withId(R.id.feedFragment)).perform(click())
            Thread.sleep(3000L)
//            onView(withId(R.id.cvLogout)).perform(click())
//            Thread.sleep(3000L)
        }
//        onView(withId(R.id.etEmail)).perform(typeText("arkarzaw412@gmail.com"))
//        onView(withId(R.id.etPassword)).perform(typeText("12345678"))
//        onView(withId(R.id.btnLogin)).perform(click())
//        Thread.sleep(5000L)
//        activityScenario?.onActivity { activity ->
//            val navController = activity.findNavController(R.id.nav_host_fragment_activity_main)
//            val currentDestinationId = navController.currentDestination?.id
//
//            // Compare the current destination ID with the expected destination ID
//            assertEquals(R.id.feedFragment, currentDestinationId)
//        }
    }

    @Test
    fun registerFlow() {
        Thread.sleep(3000L)
        onView(withId(R.id.btnRegister)).perform(click())
        onView(withId(R.id.etEmail)).perform(typeText("htetarkar007@gmail.com"))
        onView(withId(R.id.etDisplayName)).perform(typeText("Htet Arkar Zaw"))
        onView(withId(R.id.etPassword)).perform(typeText("haz@twitter2023"))
        onView(withId(R.id.etConfirmPassword)).perform(typeText("haz@twitter2023"))
        onView(withId(R.id.btnRegister)).perform(click())
        Thread.sleep(5000L)
        activityScenario?.onActivity { activity ->
            val navController = activity.findNavController(R.id.nav_host_fragment_activity_main)
            val currentDestinationId = navController.currentDestination?.id

            // Compare the current destination ID with the expected destination ID
            assertEquals(R.id.feedFragment, currentDestinationId)
        }
    }

    @Test
    fun uploadFeedFlow() {
        Thread.sleep(3000L)
        onView(withId(R.id.fab)).perform(click())
        onView(withId(R.id.etTweet)).perform(typeText("This is Post From Instrumented Testing"))
        onView(withId(R.id.btnUpload)).perform(click())
        Thread.sleep(5000L)
        activityScenario?.onActivity { activity ->
            val navController = activity.findNavController(R.id.nav_host_fragment_activity_main)
            val currentDestinationId = navController.currentDestination?.id

            // Compare the current destination ID with the expected destination ID
            assertEquals(
                R.id.feedFragment,
                currentDestinationId
            )
//                onView(withId(R.id.rvFeed)).perform(
//                    RecyclerViewActions.actionOnItemAtPosition<FeedAdapter.FeedViewHolder>(
//                        0,
//                        clickItemWithId(R.id.clFeed)
//                    )
//                )
//                onView(withId(R.id.tvTweet)).check(matches(withText("This is Post From Instrumented Testing")))
        }
    }

    fun clickItemWithId(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController, view: View) {
                val v = view.findViewById<View>(id) as View
                v.performClick()
            }
        }
    }

    private fun selectBottomNavigationItem(@IdRes itemId: Int): ViewAction {
        return object : ViewAction {
            override fun getDescription(): String {
                return "Click on BottomNavigationView item with ID: $itemId"
            }

            override fun getConstraints(): Matcher<View> {
                return allOf(
                    isDisplayed(),
                    isAssignableFrom(BottomNavigationView::class.java)
                )
            }

            override fun perform(uiController: UiController, view: View) {
                val bottomNavigationView = view as BottomNavigationView
                bottomNavigationView.selectedItemId = itemId
            }
        }
    }
}