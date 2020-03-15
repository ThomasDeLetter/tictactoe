package mobi.inthepocket.tictactoe.computers

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import mobi.inthepocket.tictactoe.game.Board
import mobi.inthepocket.tictactoe.game.Cell
import mobi.inthepocket.tictactoe.game.Player
import mobi.inthepocket.tictactoe.game.Game
import java.util.concurrent.TimeUnit

abstract class Computer(protected val game: Game, val player: Player) {
    private val disposable: Disposable

    init {
        disposable = game.turns
            .filter { it.nextPlayer == player }
            .delay(200, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .forEach { board ->
                val (row, column) = nextMove(board)
                game.fill(row, column)
            }
    }

    fun dispose() = disposable.dispose()

    protected abstract fun nextMove(board: Board): Cell
}