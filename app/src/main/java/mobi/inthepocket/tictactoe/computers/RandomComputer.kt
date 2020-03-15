package mobi.inthepocket.tictactoe.computers

import mobi.inthepocket.tictactoe.game.*

class RandomComputer(game: Game, player: Player): Computer(game, player) {
    override fun nextMove(board: Board): Cell {
        var row: Int
        var column: Int
        do {
            row = (0..2).random()
            column = (0..2).random()
        } while(!board[row, column].isEmpty)
        return Cell(row, column)
    }
}