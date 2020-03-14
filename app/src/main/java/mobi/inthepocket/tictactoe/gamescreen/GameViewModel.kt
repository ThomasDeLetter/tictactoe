package mobi.inthepocket.tictactoe.gamescreen

import androidx.lifecycle.ViewModel
import mobi.inthepocket.tictactoe.game.TicTacToe

class GameViewModel: ViewModel() {
    val game = TicTacToe()
}