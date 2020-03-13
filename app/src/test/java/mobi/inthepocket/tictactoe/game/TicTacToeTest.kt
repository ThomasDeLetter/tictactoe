package mobi.inthepocket.tictactoe.game

import io.reactivex.observers.TestObserver
import org.junit.Assert.assertFalse
import org.junit.Test

class TicTacToeTest {
    @Test
    fun `should emit initial event at start of game`() {
        val ttt = TicTacToe()
        val observer = TestObserver<Board>()
        ttt.observable.subscribe(observer)

        observer.assertValueCount(1)
    }

    @Test
    fun `should emit when cell filled`() {
        val ttt = TicTacToe()
        val observer = TestObserver<Board>()
        ttt.observable.subscribe(observer)

        ttt.fill(0, 0)

        observer.assertValueCount(2)
        observer.assertValueAt(1) {
            it.countFilled == 1
        }
    }

    @Test
    fun `should not emit when cell already filled`() {
        val ttt = TicTacToe()
        val observer = TestObserver<Board>()
        ttt.observable.subscribe(observer)

        ttt.fill(0, 0)

        assertFalse(ttt.fill(0, 0))

        observer.assertValueCount(2)
        observer.assertValueAt(1) {
            it.countFilled == 1
        }
    }

    @Test
    fun `should complete when game finished`() {
        val ttt = TicTacToe()
        val observer = TestObserver<Board>()
        ttt.observable.subscribe(observer)

        ttt.apply {
            for (i in 0..2) {
                fill(0, i) // X on first row
                fill(1, i) // O on second row
            }
        }

        observer.assertComplete()
    }

}