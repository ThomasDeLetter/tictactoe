package mobi.inthepocket.tictactoe

import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import mobi.inthepocket.tictactoe.di.gameManager
import mobi.inthepocket.tictactoe.main.MainActivity
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    var activityRule: ActivityTestRule<MainActivity>
            = ActivityTestRule(MainActivity::class.java)

    @After
    fun tearDown() {
        activityRule.activity.gameManager.endGame()
    }

    @Test
    fun shouldShowAppName() {
        onView(withId(R.id.appNameTextView))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.app_name)))
    }

    @Test
    fun shouldShowGrid() {
        onView(isRoot())
            .perform(click())
        onView(withId(R.id.grid))
            .check(matches(isDisplayed()))
    }

    @Test
    fun shouldReturnToWelcome() {
        onView(isRoot()).perform(click())

        pressBack()

        onView(withId(R.id.appNameTextView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun shouldStayOnGameScreenOnOrientationChange() {
        activityRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        onView(isRoot()).perform(click())

        activityRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        onView(withId(R.id.grid))
            .check(matches(isDisplayed()))
    }
}
