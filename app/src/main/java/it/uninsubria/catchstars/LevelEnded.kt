package it.uninsubria.catchstars

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LevelEnded (view: View) : AppCompatActivity() {

    private lateinit var ScoreView: TextView //autocompilazione risultato calcolo punteggio
    private lateinit var SettingButton: Button
    private lateinit var HomeGameButton: Button
    private lateinit var ScoreButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.popup_levelend)

        ScoreView = findViewById(R.id.scoreview)
        SettingButton = findViewById(R.id.setting)
        HomeGameButton = findViewById(R.id.home_back)
        ScoreButton = findViewById(R.id.score)

        //todo inserimento risultato calcolo score nel textview
        //riceve come parametro il risultato del calcolo punti


        SettingButton.setOnClickListener{
            val intent = Intent(this@LevelEnded, SettingActivity::class.java)
            startActivity(intent)
        }

        HomeGameButton.setOnClickListener{
            val intent = Intent(this@LevelEnded, HomeGameActivity::class.java)
            startActivity(intent)
        }

        ScoreButton.setOnClickListener{
            val intent = Intent(this@LevelEnded, ScoreActivity::class.java)
            startActivity(intent)
        }



    }
}