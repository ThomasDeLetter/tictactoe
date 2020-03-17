package mobi.inthepocket.tictactoe.computers

import mobi.inthepocket.tictactoe.game.*

class NormalComputer: Computer {
    override fun nextMove(board: Board): Cell {
        return board.cells.filter {
            it.isEmpty
        }.random()
    }
}