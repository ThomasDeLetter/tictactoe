package mobi.inthepocket.tictactoe.game

/**
 * Row class represents 3 cells in a row.
 * Can be either diagonal, horizontal or vertical.
 */
class Row(private val cells: List<Cell>) : List<Cell> by cells {
    init {
        require(cells.size == 3)
    }

    val isComplete: Boolean
        get() = cells.all { !it.isEmpty } && cells.map { it.player }.distinct().size == 1
}
