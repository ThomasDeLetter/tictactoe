package mobi.inthepocket.tictactoe.computers

import mobi.inthepocket.tictactoe.game.Board
import mobi.inthepocket.tictactoe.game.Cell

interface Computer {
    fun nextMove(board: Board): Cell
}