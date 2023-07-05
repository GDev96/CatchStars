package it.uninsubria.catchstars

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class RulesActivity : AppCompatActivity(){

    private lateinit var backButton : ImageButton
    private lateinit var startButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //permette l'apertura dell'activity come finestra popup
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = layoutParams

        setContentView(R.layout.rules_activity)

        backButton = findViewById(R.id.Ann_miss)
        startButton = findViewById(R.id.Partenza)

        backButton.setOnClickListener(){
            val intent = Intent(this@RulesActivity, HomeGameActivity::class.java)
            startActivity(intent)
        }

        startButton.setOnClickListener(){
            val intent = Intent(this@RulesActivity, GameActivity::class.java)
            startActivity(intent)

        }
    }

}