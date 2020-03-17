package mobi.inthepocket.tictactoe.gamescreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.HORIZONTAL
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.GridLayoutManager
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.checkedChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.game_screen.*
import mobi.inthepocket.tictactoe.R
import mobi.inthepocket.tictactoe.computers.HardComputer
import mobi.inthepocket.tictactoe.computers.NormalComputer
import mobi.inthepocket.tictactoe.di.Difficulty.HARD
import mobi.inthepocket.tictactoe.di.Difficulty.NORMAL
import mobi.inthepocket.tictactoe.di.GameManager
import mobi.inthepocket.tictactoe.game.Game
import mobi.inthepocket.tictactoe.game.Player
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GameFragment @Inject constructor(
    private val gridAdapter: BoardGridAdapter,
    private val game: Game,
    private val humanPlayer: Player,
    private val gameManager: GameManager): Fragment(R.layout.game_screen) {

    private val gridLayoutManager = GridLayoutManager(context, 3)
    private val disposable = CompositeDisposable()

    @Inject
    fun setupComputer(normalComputer: NormalComputer, hardComputer: HardComputer) {
        game.turns
            .filter { it.nextPlayer != humanPlayer && !it.isFinished}
            .delay(1000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { board ->
                val computer = if (gameManager.difficulty == HARD) hardComputer else normalComputer
                val (row, column) = computer.nextMove(board)
                game.fill(row, column)
            }.addTo(disposable)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        grid.apply {
            layoutManager = gridLayoutManager
            adapter = gridAdapter

            addItemDecoration(DividerItemDecoration(context, HORIZONTAL))
            addItemDecoration(DividerItemDecoration(context, VERTICAL))
        }

        when (gameManager.difficulty) {
            NORMAL -> difficulty.check(normalComputer.id)
            HARD -> difficulty.check(hardComputer.id)
        }

        difficulty.checkedChanges()
            .skipInitialValue()
            .map { if (normalComputer.isChecked) NORMAL else HARD }
            .subscribe {
                gameManager.difficulty = it
            }.addTo(disposable)

        game.turns
            .forEach {
                when (it.nextPlayer) {
                    humanPlayer -> nextPlayerTextView.text = resources.getString(R.string.user_turn, it.nextPlayer)
                    else -> nextPlayerTextView.text = resources.getString(R.string.computer_turn)
                }

            }.addTo(disposable)

        restart.visibility = View.GONE
        game.turns
            .lastElement()
            .subscribe { board ->
                restart.visibility = View.VISIBLE
                when {
                    board.isDraw -> nextPlayerTextView.text = resources.getString(R.string.game_draw)
                    board.winner == humanPlayer -> nextPlayerTextView.text = resources.getString(R.string.game_won)
                    else -> nextPlayerTextView.text = resources.getString(R.string.game_lost)
                }
            }
            .addTo(disposable)

        restart.clicks()
            .subscribe { gameManager.startGame(if (humanPlayer == Player.X) Player.O else Player.X) }
            .addTo(disposable)
    }

    override fun onDestroyView() {
        grid.adapter = null
        disposable.dispose()
        super.onDestroyView()
    }
}