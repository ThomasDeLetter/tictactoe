package mobi.inthepocket.tictactoe.gamescreen

import androidx.lifecycle.ViewModel
import mobi.inthepocket.tictactoe.computers.Computer
import mobi.inthepocket.tictactoe.computers.RandomComputer
import mobi.inthepocket.tictactoe.game.Player
import mobi.inthepocket.tictactoe.game.Game

class GameViewModel: ViewModel() {
    val game = Game()
    val player: Player = Player.O
    val computer: Computer = RandomComputer(game, Player.X)
}