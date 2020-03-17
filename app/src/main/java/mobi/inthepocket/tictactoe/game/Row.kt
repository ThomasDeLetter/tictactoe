package mobi.inthepocket.tictactoe.game

/**
 * Row class represents 3 cells in a row.
 * Can be either diagonal, horizontal or vertical.
 */
class Row(private val cells: List<Cell>) : List<Cell> by cells {
    init {
        require(cells.size == 3)
    }

    val countFilled: Int by lazy {
        cells.count { !it.isEmpty }
    }

    val hasEmptyCells: Boolean
        inline get() = countFilled != 3

    val playersInRow = cells.filter { !it.isEmpty }.map { it.player }.distinct()

    val isComplete: Boolean
        get() = !hasEmptyCells && playersInRow.size == 1
}
