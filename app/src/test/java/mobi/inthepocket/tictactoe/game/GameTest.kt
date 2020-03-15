package mobi.inthepocket.tictactoe.game

import io.reactivex.observers.TestObserver
import org.junit.Assert.assertFalse
import org.junit.Test

class GameTest {
    @Test
    fun `should emit initial event at start of game`() {
        val game = Game()
        val observer = TestObserver<Board>()
        game.turns.subscribe(observer)

        observer.assertValueCount(1)
    }

    @Test
    fun `should emit when cell filled`() {
        val game = Game()
        val observer = TestObserver<Board>()
        game.turns.subscribe(observer)

        game.fill(0, 0)

        observer.assertValueCount(2)
        observer.assertValueAt(1) {
            it.countFilled == 1
        }
    }

    @Test
    fun `should not emit when cell already filled`() {
        val game = Game()
        val observer = TestObserver<Board>()
        game.turns.subscribe(observer)

        game.fill(0, 0)

        assertFalse(game.fill(0, 0))

        observer.assertValueCount(2)
        observer.assertValueAt(1) {
            it.countFilled == 1
        }
    }

    @Test
    fun `should complete when game finished`() {
        val game = Game()
        val observer = TestObserver<Board>()
        game.turns.subscribe(observer)

        game.apply {
            for (i in 0..2) {
                fill(0, i) // X on first row
                fill(1, i) // O on second row
            }
        }

        observer.assertComplete()
    }

}