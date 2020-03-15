package mobi.inthepocket.tictactoe.di

import androidx.fragment.app.Fragment
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
annotation class FragmentKey(val fragment : KClass<out Fragment>)