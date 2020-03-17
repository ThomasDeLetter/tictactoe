package mobi.inthepocket.tictactoe.di

import androidx.fragment.app.Fragment
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import mobi.inthepocket.tictactoe.computers.HardComputer
import mobi.inthepocket.tictactoe.computers.NormalComputer
import mobi.inthepocket.tictactoe.game.Game
import mobi.inthepocket.tictactoe.gamescreen.GameFragment

@Module(includes = [AssistedInject_GameModule::class])
@AssistedModule
interface GameModule {
    companion object {
        @GameScope
        @Provides
        fun game() = Game()

        @GameScope
        @Provides
        fun normalComputer() = NormalComputer()

        @GameScope
        @Provides
        fun hardComputer() = HardComputer()
    }

    @Binds
    @IntoMap
    @FragmentKey(GameFragment::class)
    fun bindGameFragment(fragment : GameFragment) : Fragment
}