package mobi.inthepocket.tictactoe.gamescreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.HORIZONTAL
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.GridLayoutManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.game_screen.*
import mobi.inthepocket.tictactoe.R
import mobi.inthepocket.tictactoe.di.GameManager
import mobi.inthepocket.tictactoe.game.Game
import javax.inject.Inject

class GameFragment @Inject constructor(private val gridAdapter: BoardGridAdapter, private val game: Game, private val gameManager: GameManager): Fragment(R.layout.game_screen) {
    private val gridLayoutManager = GridLayoutManager(context, 3)
    private val disposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        grid.apply {
            layoutManager = gridLayoutManager
            adapter = gridAdapter

            addItemDecoration(DividerItemDecoration(context, HORIZONTAL))
            addItemDecoration(DividerItemDecoration(context, VERTICAL))
        }

        game.turns
            .forEach {
                if (it.isFinished)
                    nextPlayer.text = ""
                else
                    nextPlayer.text = "Next player: ${it.nextPlayer}"
            }.addTo(disposable)

        game.turns
            .lastElement()
            .subscribe { gameManager.startGame() }
            .addTo(disposable)
    }

    override fun onDestroyView() {
        grid.adapter = null
        disposable.dispose()
        super.onDestroyView()
    }
}