package mobi.inthepocket.tictactoe.di

import androidx.fragment.app.Fragment
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import mobi.inthepocket.tictactoe.gamescreen.GameFragment

@Module(includes = [AssistedInject_GameModule::class])
@AssistedModule
interface GameModule {
    @Binds
    @IntoMap
    @FragmentKey(GameFragment::class)
    fun bindGameFragment(fragment : GameFragment) : Fragment
}