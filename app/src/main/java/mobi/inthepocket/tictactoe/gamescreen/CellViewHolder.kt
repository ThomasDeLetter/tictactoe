package mobi.inthepocket.tictactoe.gamescreen

import android.graphics.Typeface
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.view.clicks
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.withLatestFrom
import kotlinx.android.synthetic.main.cell.view.*
import mobi.inthepocket.tictactoe.game.Player
import mobi.inthepocket.tictactoe.game.Game

class CellViewHolder @AssistedInject constructor(private val game: Game, private val player: Player, @Assisted view: View): RecyclerView.ViewHolder(view) {
    private var disposable = CompositeDisposable()

    private val row
        inline get() = adapterPosition / 3

    private val column
        inline get() = adapterPosition % 3

    fun attach() {
        disposable = CompositeDisposable()

        game.turns
            .map { it[row, column] }
            .forEach {
                itemView.cellText.text = it.player?.toString() // TODO set image with animation
            }.addTo(disposable)

        game.turns
            .lastElement()
            .filter { !it.isDraw && it[row, column] in it.winningRow!!}
            .subscribe {
                itemView.cellText.setTypeface(null, Typeface.BOLD)
            }.addTo(disposable)

        itemView.clicks()
            .withLatestFrom(game.turns) { _, board -> board }
            .filter { player == it.nextPlayer }
            .forEach {
                game.fill(row, column)
            }.addTo(disposable)
    }

    fun detach() = disposable.dispose()

    @AssistedInject.Factory
    interface Factory {
        fun create(view: View): CellViewHolder
    }
}