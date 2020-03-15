package mobi.inthepocket.tictactoe.startscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.start_screen.*
import mobi.inthepocket.tictactoe.R
import mobi.inthepocket.tictactoe.di.GameManager
import javax.inject.Inject

class StartFragment @Inject constructor(private val gameManager: GameManager): Fragment(R.layout.start_screen) {
    lateinit var disposable: Disposable
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disposable = button.clicks().forEach { gameManager.startGame() }
    }

    override fun onDestroyView() {
        disposable.dispose()
        super.onDestroyView()
    }
}
