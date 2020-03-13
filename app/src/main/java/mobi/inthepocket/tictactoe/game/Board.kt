package mobi.inthepocket.tictactoe.game

class Board private constructor(private val grid: List<Cell>) {

    constructor(): this((0..8).map { Cell(it / 3, it % 3) })

    val nextPlayer: Player by lazy {
        if (countFilled % 2 == 0) Player.X else Player.O
    }

    val countFilled: Int by lazy {
        grid.count { !it.isEmpty }
    }


    private val rows: List<Row> by lazy {
        grid.chunked(3).map { Row(it) }
    }

    private val columns: List<Row> by lazy {
        grid.withIndex()
            .groupBy { it.index % 3 }
            .values
            .map { indexedColumn ->
                Row(indexedColumn.map { it.value })
            }
    }

    private val diagonals: List<Row> by lazy {
        (0..2 step 2).map { startIndex ->
            Row(listOf(grid[startIndex], grid[4], grid[8 - startIndex]))
        }
    }

    val winningRow: Row? by lazy {
        (diagonals + rows + columns).firstOrNull { it.isComplete }
    }

    val isEmpty: Boolean = countFilled == 0

    val winner: Player? = winningRow?.first()?.player

    val isFinished: Boolean = winningRow != null || countFilled == 9

    val isDraw: Boolean = winningRow == null && isFinished

    fun fill(row: Int, column: Int): Board {
        check(!isFinished)
        check(this[row, column].isEmpty)
        return Board(grid.toMutableList().apply {
            this[3 * row + column] = Cell(row, column, nextPlayer)
        })
    }

    operator fun get(row: Int, column: Int): Cell = grid[3 * row + column]
}
