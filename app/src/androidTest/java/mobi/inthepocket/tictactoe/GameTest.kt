package mobi.inthepocket.tictactoe

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import mobi.inthepocket.tictactoe.di.gameManager
import mobi.inthepocket.tictactoe.game.Player
import mobi.inthepocket.tictactoe.main.MainActivity
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.AssertionError


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
        onView(allOf(withParent(withRecyclerView(R.id.grid).atPosition(0)), withId(R.id.cell)))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun shouldNotBeEmptyWhenClicked() {
        onView(withRecyclerView(R.id.grid).atPosition(0))
            .perform(click())

        onView(allOf(withParent(withRecyclerView(R.id.grid).atPosition(0)), withId(R.id.cell)))
            .check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayUserTurnText() {
        onView(withId(R.id.nextPlayerTextView))
            .check(matches(withText(activityRule.activity.getString(R.string.user_turn, Player.X))))
    }

    @Test
    fun shouldDisplayComputerTurnText() {
        onView(withRecyclerView(R.id.grid).atPosition(0))
            .perform(click())
        onView(withId(R.id.nextPlayerTextView))
            .check(matches(withText(R.string.computer_turn)))
    }

    @Test
    fun shouldShowRestartButton() {
        for (i in (0..45)) {
            onView(withRecyclerView(R.id.grid).atPosition(i % 9))
                .perform(click())
            Thread.sleep(1100)
            try {
                onView(withId(R.id.restart)).check(matches(isDisplayed()))
                return
            } catch (ignored: AssertionError) {
                println("error")
            }
        }
        onView(withId(R.id.restart)).check(matches(isDisplayed()))
    }
}
