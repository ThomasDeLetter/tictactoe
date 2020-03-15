package mobi.inthepocket.tictactoe.di

import androidx.fragment.app.Fragment
import io.reactivex.subjects.BehaviorSubject
import mobi.inthepocket.tictactoe.computers.RandomComputer
import mobi.inthepocket.tictactoe.game.Game
import mobi.inthepocket.tictactoe.game.Player
import mobi.inthepocket.tictactoe.gamescreen.GameFragment
import mobi.inthepocket.tictactoe.main.MainActivity
import mobi.inthepocket.tictactoe.startscreen.StartFragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameManager @Inject constructor(private val appComponent: ApplicationComponent, private val builder: GameComponent.Builder) {
    private var nextPlayer: Player = Player.X

    private var gameComponent: GameComponent? = null

    private var navigator = BehaviorSubject.createDefault<Class<out Fragment>>(StartFragment::class.java)

    private fun buildGameComponent(): GameComponent {
        val game = Game()
        val computer = RandomComputer(game, nextPlayer)
        return builder.player(if (nextPlayer == Player.X) Player.O else Player.X)
            .game(game)
            .computer(computer)
            .build()
    }

    fun startGame() {
        val game = Game()
        val computer = RandomComputer(game, nextPlayer)
        gameComponent = builder.player(if (nextPlayer == Player.X) Player.O else Player.X)
            .game(game)
            .computer(computer)
            .build()
        navigator.onNext(GameFragment::class.java)
    }

    fun endGame() {
        gameComponent = null
        navigator.onNext(StartFragment::class.java)
    }

    fun navigator() = navigator.hide()

    fun inject(activity: MainActivity) {
        if (gameComponent != null)
            gameComponent?.inject(activity)
        else
            appComponent.inject(activity)
    }
}
