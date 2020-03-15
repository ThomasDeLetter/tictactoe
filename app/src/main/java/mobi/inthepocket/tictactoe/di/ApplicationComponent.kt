package mobi.inthepocket.tictactoe.di

import dagger.BindsInstance
import dagger.Component
import mobi.inthepocket.tictactoe.main.MainActivity
import javax.inject.Singleton

@Component(modules = [FragmentFactoryModule::class])
@Singleton
interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(app: App): Builder
        fun build(): ApplicationComponent
    }

    val builder: GameComponent.Builder

    fun inject(app: App)
    fun inject(activity: MainActivity)
}