package mobi.inthepocket.tictactoe.game

class Cell(val row: Int, val column: Int, val player: Player?) {
    constructor(row: Int, column: Int): this(row, column, null)

    val isEmpty: Boolean = player == null
}