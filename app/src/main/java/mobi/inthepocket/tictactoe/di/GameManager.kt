package mobi.inthepocket.tictactoe.di

import androidx.fragment.app.Fragment
import io.reactivex.subjects.BehaviorSubject
import mobi.inthepocket.tictactoe.game.Player
import mobi.inthepocket.tictactoe.gamescreen.GameFragment
import mobi.inthepocket.tictactoe.main.MainActivity
import mobi.inthepocket.tictactoe.welcomescreen.WelcomeFragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameManager @Inject constructor(private val appComponent: ApplicationComponent, private val builder: GameComponent.Builder) {

    private var gameComponent: GameComponent? = null

    private var navigator = BehaviorSubject.createDefault<Class<out Fragment>>(WelcomeFragment::class.java)

    var difficulty: Difficulty = Difficulty.HARD

    fun startGame(humanPlayer: Player) {
        gameComponent = builder
            .humanPlayer(humanPlayer)
            .build()
        gameComponent?.inject(this)
        navigator.onNext(GameFragment::class.java)
    }

    fun endGame(): Boolean {
        if (gameComponent != null) {
            gameComponent = null
            navigator.onNext(WelcomeFragment::class.java)
            return true
        }
        return false
    }

    fun navigator() = navigator.hide()

    fun inject(activity: MainActivity) {
        if (gameComponent != null)
            gameComponent?.inject(activity)
        else
            appComponent.inject(activity)
    }
}

enum class Difficulty {
    NORMAL, HARD
}