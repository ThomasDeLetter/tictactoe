package mobi.inthepocket.tictactoe.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import mobi.inthepocket.tictactoe.startscreen.StartFragment

@Module
interface FragmentFactoryModule {
    @Binds
    fun bindFragmentFactory(factory: DaggerFragmentFactory): FragmentFactory

    @Binds
    @IntoMap
    @FragmentKey(StartFragment::class)
    fun bindStartFragment(fragment : StartFragment) : Fragment

}

