package mobi.inthepocket.tictactoe.welcomescreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.disposables.Disposable
import mobi.inthepocket.tictactoe.R
import mobi.inthepocket.tictactoe.di.GameManager
import mobi.inthepocket.tictactoe.game.Player
import javax.inject.Inject

class WelcomeFragment @Inject constructor(private val gameManager: GameManager): Fragment(R.layout.welcome_screen) {
    private lateinit var disposable: Disposable
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disposable = view.clicks().forEach { gameManager.startGame(Player.X) }
    }

    override fun onDestroyView() {
        disposable.dispose()
        super.onDestroyView()
    }
}
