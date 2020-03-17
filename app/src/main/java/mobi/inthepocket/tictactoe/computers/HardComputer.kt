package mobi.inthepocket.tictactoe.computers

import mobi.inthepocket.tictactoe.game.Board
import mobi.inthepocket.tictactoe.game.Cell
import mobi.inthepocket.tictactoe.game.Player
import mobi.inthepocket.tictactoe.game.Row

class HardComputer : Computer {
    override fun nextMove(board: Board): Cell {
        val computer = board.nextPlayer
        val opponent = if (board.nextPlayer == Player.X) Player.O else Player.X

        val sortedRows = board.rows
            .sortedWith(compareBy<Row> { it.playersInRow.size }
                .thenBy { if (computer in it.playersInRow) 0 else 1 }
                .thenBy { - it.countFilled })

        val twoInARow = sortedRows.firstOrNull { it.countFilled == 2 && it.playersInRow.size == 1}
        if (twoInARow != null) {
            return twoInARow.first { it.isEmpty }
        }

        val corners = corners(board)

        if (board.isEmpty) {
            return corners.random()
        }

        val middle = middle(board)
        if (board.countFilled == 1) {
            return when (board.cells.first { !it.isEmpty }) {
                middle -> corners.random()
                else -> middle
            }
        }

        if (board.countFilled == 2) {
            val previousCell = board.cells.first { !it.isEmpty && it.player == computer}
            val opposite = opposite(previousCell, board)
            return when {
                previousCell in corners && opposite.isEmpty -> opposite
                previousCell in corners && !opposite.isEmpty -> middle
                previousCell == middle -> corners.filter { it.isEmpty }.random()
                else -> sideRow(opposite, board).filter { it.isEmpty && it != opposite }.random()
            }
        }

        if (board.countFilled == 3 && middle.player == computer && corners.filter { !it.isEmpty }.size == 2) {
            return sides(board).random()
        }

        if (board.countFilled in 3..4) {
            val rows = board.rows.filter { it.playersInRow == listOf(if (board.countFilled == 3) opponent else computer) }
            for (row1 in rows) {
                for (row2 in rows) {
                    if (row1.first { !it.isEmpty } != row2.first { !it.isEmpty }) {
                        val intersect = row1.intersect(row2)
                        if (intersect.size == 1 && intersect.first().isEmpty) {
                            return intersect.first()
                        }
                    }
                }
            }
        }

        return sortedRows.first { it.hasEmptyCells }.first { it.isEmpty }
    }

    private companion object HelperMethods {
        private fun corners(board: Board): List<Cell> {
            return board.cells.filter { (row, column) ->
                row in (0..2 step 2) && column in (0..2 step 2)
            }
        }

        private fun sides(board: Board): List<Cell> {
            return board.cells - corners(board) - middle(board)
        }

        private fun middle(board: Board) = board[1, 1]

        private fun opposite(cell: Cell, board: Board): Cell {
            val row = -(cell.row - 1) + 1
            val column = -(cell.column - 1) + 1
            return board[row, column]
        }

        private fun sideRow(cell: Cell, board: Board): Row {
            val middle = middle(board)
            return board.rows
                .first { !it.contains(middle) && it.contains(cell) }
        }
    }
}