package mobi.inthepocket.tictactoe.game

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

open class TicTacToe {
    private var board = Board()
    private val subject: BehaviorSubject<Board> = BehaviorSubject.createDefault(board)

    val observable: Observable<Board> = subject

    fun fill(row: Int, column: Int): Boolean {
        if (!board.isFinished && board[row, column].isEmpty) {
            board = board.fill(row, column)
            subject.onNext(board)
            if (board.isFinished)
                subject.onComplete()
            return true
        }
        return false
    }
}
