package mobi.inthepocket.tictactoe.computers

import mobi.inthepocket.tictactoe.game.Board
import org.junit.Test
import org.junit.Assert.*

class NormalComputerTest {
    @Test
    fun `should not fill already filled cell`() {
        val normalComputer = NormalComputer()
        val board = Board()
            .fill(0, 0)

        val (row, column) = normalComputer.nextMove(board)
        assertFalse(row == 0 && column == 0)
    }
}