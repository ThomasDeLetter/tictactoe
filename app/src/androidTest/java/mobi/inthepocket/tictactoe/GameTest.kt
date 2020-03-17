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
import mobi.inthepocket.tictactoe.game.Player
import mobi.inthepocket.tictactoe.main.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class GameTest {
    @get:Rule
    var activityRule: ActivityTestRule<MainActivity>
            = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setup() {
        activityRule.activity.gameManager.startGame(Player.X)
    }

    @After
    fun tearDown() {
        activityRule.activity.gameManager.endGame()
    }

    @Test
    fun shouldShowGrid() {
        onView(withId(R.id.grid))
            .check(matches(isDisplayed()))
    }

    @Test
    fun shouldBeEmptyAtStart() {
        onView(withId(R.id.grid))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition(
                    ITEM_BELOW_THE_FOLD,
                    click()
                )
            )
    }
}
