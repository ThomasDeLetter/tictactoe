package mobi.inthepocket.tictactoe.gamescreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.HORIZONTAL
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.GridLayoutManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.game.*
import mobi.inthepocket.tictactoe.R

class GameFragment: Fragment(R.layout.game) {
    private val gameViewModel by lazy {
        ViewModelProvider(this).get(GameViewModel::class.java)
    }
    private val gridLayoutManager = GridLayoutManager(context, 3)

    private val disposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        grid.apply {
            layoutManager = gridLayoutManager
            adapter = BoardGridAdapter(gameViewModel.game)

            addItemDecoration(DividerItemDecoration(context, HORIZONTAL))
            addItemDecoration(DividerItemDecoration(context, VERTICAL))
        }

        gameViewModel.game.turns
            .forEach {
                if (it.isFinished)
                    nextPlayer.text = ""
                else
                    nextPlayer.text = "Next player: ${it.nextPlayer}"
            }.addTo(disposable)
    }

    override fun onDestroyView() {
        grid.adapter = null
        disposable.dispose()
        super.onDestroyView()
    }
}