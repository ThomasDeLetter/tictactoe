package mobi.inthepocket.tictactoe.game

class Board private constructor(val cells: List<Cell>) {

    constructor(): this((0..8).map { Cell(it / 3, it % 3) })

    val nextPlayer: Player by lazy {
        if (countFilled % 2 == 0) Player.X else Player.O
    }

    val countFilled: Int by lazy {
        cells.count { !it.isEmpty }
    }

    private val horizontalRows: List<Row> by lazy {
        cells.chunked(3).map { Row(it) }
    }

    private val verticalRows: List<Row> by lazy {
        cells.withIndex()
            .groupBy { it.index % 3 }
            .values
            .map { indexedColumn ->
                Row(indexedColumn.map { it.value })
            }
    }

    private val diagonals: List<Row> by lazy {
        (0..2 step 2).map { startIndex ->
            Row(listOf(cells[startIndex], cells[4], cells[8 - startIndex]))
        }
    }

    val rows: List<Row>
        get() = diagonals + horizontalRows + verticalRows

    val winningRow: Row? by lazy {
        rows.firstOrNull { it.isComplete }
    }

    val isEmpty: Boolean
        inline get() = countFilled == 0

    val winner: Player?
        inline get() = winningRow?.first()?.player

    val isFinished: Boolean
        inline get() = winningRow != null || countFilled == 9

    val isDraw: Boolean
        inline get() = winningRow == null && isFinished

    fun fill(row: Int, column: Int): Board {
        check(!isFinished)
        check(this[row, column].isEmpty)
        return Board(cells.toMutableList().apply {
            this[3 * row + column] = Cell(row, column, nextPlayer)
        })
    }

    operator fun get(row: Int, column: Int): Cell = cells[3 * row + column]
}
