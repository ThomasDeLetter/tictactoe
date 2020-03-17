package mobi.inthepocket.tictactoe.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentFactory
import io.reactivex.disposables.Disposable
import mobi.inthepocket.tictactoe.R
import mobi.inthepocket.tictactoe.di.gameManager
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        gameManager.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        disposable = gameManager.navigator()
            .forEach { fragment ->
                gameManager.inject(this)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment, null)
                    .commit()
            }
    }

    @Inject
    fun injectFactory(factory: FragmentFactory) {
        supportFragmentManager.fragmentFactory = factory
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    override fun onBackPressed() {
        if (!gameManager.endGame())
            super.onBackPressed()
    }
}
