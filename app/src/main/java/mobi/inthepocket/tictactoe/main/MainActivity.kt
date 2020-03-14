package mobi.inthepocket.tictactoe.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import mobi.inthepocket.tictactoe.R
import mobi.inthepocket.tictactoe.gamescreen.GameFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, GameFragment())
                .commit()
        }
    }
}
