package mobi.inthepocket.tictactoe.gamescreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import mobi.inthepocket.tictactoe.R
import javax.inject.Inject

class BoardGridAdapter @Inject constructor(private val factory: CellViewHolder.Factory): Adapter<CellViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellViewHolder
            = factory.create(LayoutInflater.from(parent.context).inflate(R.layout.cell, parent, false))

    override fun getItemCount(): Int = 9

    override fun onBindViewHolder(holder: CellViewHolder, position: Int) {}

    override fun onViewDetachedFromWindow(holder: CellViewHolder) = holder.detach()

    override fun onViewAttachedToWindow(holder: CellViewHolder) = holder.attach()
}
