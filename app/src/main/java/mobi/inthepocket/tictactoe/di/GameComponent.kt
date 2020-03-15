package mobi.inthepocket.tictactoe.di

import dagger.BindsInstance
import dagger.Subcomponent
import mobi.inthepocket.tictactoe.computers.Computer
import mobi.inthepocket.tictactoe.game.Game
import mobi.inthepocket.tictactoe.game.Player
import mobi.inthepocket.tictactoe.main.MainActivity

@Subcomponent(modules = [GameModule::class])
@GameScope
interface GameComponent {
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun player(player: Player): Builder
        @BindsInstance
        fun computer(computer: Computer): Builder
        @BindsInstance
        fun game(game: Game): Builder
        fun build(): GameComponent
    }

    fun inject(activity: MainActivity)

    val game: Game
}
