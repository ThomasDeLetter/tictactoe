package mobi.inthepocket.tictactoe.di

import android.app.Application
import android.content.Context
import javax.inject.Inject

class App: Application() {

    private val component by lazy {
        DaggerApplicationComponent.builder()
            .app(this)
            .build()
    }

    @Inject
    lateinit var gameManager: GameManager

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}

val Context.gameManager: GameManager
    get() = (this.applicationContext as App).gameManager