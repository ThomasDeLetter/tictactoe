package mobi.inthepocket.tictactoe.computers

import mobi.inthepocket.tictactoe.game.Board
import org.junit.Assert.*
import org.junit.Test

class HardComputerTest {
    @Test
    fun `should fill empty cell when 2 in a row`() {
        val hardComputer = HardComputer()
        val board = Board()
            .fill(0, 0)
            .fill(1, 0)
            .fill(0, 1)

        val (row, column) = hardComputer.nextMove(board)
        assertEquals(0, row)
        assertEquals(2, column)
    }

    @Test
    fun `should fill one of the corners on empty board`() {
        val hardComputer = HardComputer()
        val board = Board()

        val (row, column) = hardComputer.nextMove(board)
        assertTrue(
            row in (0..2 step 2) && column in (0..2 step 2)
        )
    }

    @Test
    fun `should fill middle on first turn as player O if not taken`() {
        val hardComputer = HardComputer()
        val board = Board()
            .fill(0, 0)

        val (row, column) = hardComputer.nextMove(board)
        assertEquals(1, row)
        assertEquals(1, column)
    }

    @Test
    fun `should fill corner on first turn as player O if middle is taken`() {
        val hardComputer = HardComputer()
        val board = Board()
            .fill(1, 1)

        val (row, column) = hardComputer.nextMove(board)
        assertTrue(
            row in (0..2 step 2) && column in (0..2 step 2)
        )
    }

    @Test
    fun `should fill opposite corner on second turn as player X`() {
        val hardComputer = HardComputer()
        val board = Board()
            .fill(0, 0)
            .fill(1, 1)

        val (row, column) = hardComputer.nextMove(board)
        assertEquals(2, row)
        assertEquals(2, column)
    }

    @Test
    fun `should fill middle on second turn as player X if opposite corner taken`() {
        val hardComputer = HardComputer()
        val board = Board()
            .fill(0, 0)
            .fill(2, 2)

        val (row, column) = hardComputer.nextMove(board)
        assertEquals(1, row)
        assertEquals(1, column)
    }

    @Test
    fun `should fill corner on second turn as player X if first turn filled middle`() {
        val hardComputer = HardComputer()
        val board = Board()
            .fill(1, 1)
            .fill(0, 0)

        val (row, column) = hardComputer.nextMove(board)
        assertTrue(
            row in (0..2 step 2) && column in (0..2 step 2)
        )
    }

    @Test
    fun `should fill corner opposite to X on second turn as player X if first turn filled side`() {
        val hardComputer = HardComputer()
        val board = Board()
            .fill(0, 1)
            .fill(1, 1)

        val (row, column) = hardComputer.nextMove(board)
        assertEquals(row, 2)
        assertTrue(column in (0..2 step 2))
    }

    @Test
    fun `should fill intersect of 2 rows with X on second turn as player O`() {
        val hardComputer = HardComputer()
        val board = Board()
            .fill(0, 0)
            .fill(1, 1)
            .fill(2, 1)
        // X _ _
        // _ O _
        // _ X _
        val (row, column) = hardComputer.nextMove(board)
        assertEquals(2, row)
        assertEquals(0, column)
    }

    @Test
    fun `should fill intersect of 2 rows with X on third turn as player X`() {
        val hardComputer = HardComputer()
        val board = Board()
            .fill(0, 0)
            .fill(0, 1)
            .fill(2, 1)
            .fill(1, 0)
        // X O _
        // O _ _
        // _ X _
        val (row, column) = hardComputer.nextMove(board)
        assertEquals(2, row)
        assertEquals(2, column)
    }

    @Test
    fun `should fill side cell on second turn as player O if 2 X's in opposite corners`() {
        val hardComputer = HardComputer()
        val board = Board()
            .fill(0, 0)
            .fill(1, 1)
            .fill(2, 2)
        val (row, column) = hardComputer.nextMove(board)

        assertTrue(row in (0..2) && column == 1 || row == 1 && column in (0..2))
    }

    @Test
    fun `should fill a row not containing opponent`() {
        val hardComputer = HardComputer()
        val board = Board()
            .fill(0, 0)
            .fill(0, 1)
            .fill(2, 1)
            .fill(1, 0)
            .fill(1, 2)
        // X O _
        // O _ X
        // _ X _
        val (row, column) = hardComputer.nextMove(board)
        assertTrue((0..2).any { it ->
            it == row && column == 2 - it
        })
    }

}