package mobi.inthepocket.tictactoe.game

import org.junit.Test

import org.junit.Assert.*

class BoardTest {
    companion object {
        // X O X
        // X O O
        // O X X
        val DRAW = { board: Board ->
            board.fill(0, 0)
                .fill(0, 1)
                .fill(0, 2)
                .fill(1, 1)
                .fill(1, 0)
                .fill(1, 2)
                .fill(2, 1)
                .fill(2, 0)
                .fill(2, 2)
        }

        // X X X
        // O O
        //
        val ROW = { board: Board ->
            board.fill(0, 0)
                .fill(1, 0)
                .fill(0, 1)
                .fill(1, 1)
                .fill(0, 2)
        }

        // X O O
        //   X
        //     X
        val DIAGONAL = { board: Board ->
            board.fill(0, 0)
                .fill(0, 1)
                .fill(1, 1)
                .fill(0, 2)
                .fill(2, 2)
        }
    }

    @Test
    fun `should be empty on start`() {
        val board = Board()

        assertTrue(board.isEmpty)
    }

    @Test
    fun `should be X's turn at start`() {
        val board = Board()

        assertEquals(Player.X, board.nextPlayer)
    }

    @Test
    fun `should be able to fill empty cell`() {
        val board = Board()
            .fill(0, 0)

        assertEquals(1, board.countFilled)
    }

    @Test
    fun `should not be able to fill cell twice`() {
        val board = Board()
            .fill(0, 0)

        val result = kotlin.runCatching { board.fill(0, 0) }
            .exceptionOrNull()
        assertTrue(result is IllegalStateException)
    }

    @Test
    fun `should not be finished at start`() {
        val board = Board()

        assertFalse(board.isFinished)
    }

    @Test
    fun `should be finished when all cells are full`() {
        val board = Board()
            .let(DRAW)

        assertEquals(9, board.countFilled)
        assertTrue(board.isFinished)
        assertTrue(board.isDraw)
    }

    @Test
    fun `should be finished when 3-in-a-row`() {
        val board = Board()
            .let(ROW)

        assertTrue(board.isFinished)
    }

    @Test
    fun `should be finished when 3-in-a-row diagonal`() {
        val board = Board()
            .let(DIAGONAL)

        assertTrue(board.isFinished)
    }

    @Test(expected = IllegalStateException::class)
    fun `should not be able to fill when finished`() {
        val board = Board()
            .let(ROW)

        board.fill(2, 1)
        kotlin.runCatching {  }
    }

    @Test
    fun `should have winner when 3-in-a-row`() {
        val board = Board()
            .let(ROW)

        assertNotNull(board.winner)
        assertEquals(board.winner!!, Player.X)
    }
}
