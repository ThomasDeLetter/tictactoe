package mobi.inthepocket.tictactoe.game

import io.reactivex.Observable
import io.reactivex.subjects.ReplaySubject

open class Game {
    private val subject: ReplaySubject<Board> = ReplaySubject.createWithSize<Board>(1).apply {
        onNext(Board())
    }

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
