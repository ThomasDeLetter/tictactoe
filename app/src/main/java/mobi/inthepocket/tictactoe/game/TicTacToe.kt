package mobi.inthepocket.tictactoe.game

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

open class TicTacToe {
    private val subject: BehaviorSubject<Board> = BehaviorSubject.createDefault(Board())

    private val board
        inline get() = subject.value!!

    val turns: Observable<Board> = subject

    fun fill(row: Int, column: Int): Boolean {
        if (!subject.hasComplete() && board[row, column].isEmpty) {
            subject.onNext(board.fill(row, column))
            if (board.isFinished)
                subject.onComplete()
            return true
        }
        return false
    }
}
