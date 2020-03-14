package mobi.inthepocket.tictactoe.gamescreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import mobi.inthepocket.tictactoe.R
import mobi.inthepocket.tictactoe.game.TicTacToe

class BoardGridAdapter(private val game: TicTacToe): Adapter<CellViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellViewHolder =
        CellViewHolder(game, LayoutInflater.from(parent.context).inflate(R.layout.cell, parent, false))

    override fun getItemCount(): Int = 9

    override fun onBindViewHolder(holder: CellViewHolder, position: Int) {}

    override fun onViewDetachedFromWindow(holder: CellViewHolder) = holder.detach()

    override fun onViewAttachedToWindow(holder: CellViewHolder) = holder.attach()
}