package it.uninsubria.catchstars

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TableLayout

class ScoreActivity : Activity(){

    private lateinit var TableScore: TableLayout
    private lateinit var BackButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = layoutParams

        setContentView(R.layout.score)

        TableScore = findViewById(R.id.tablescore)
        //BackButton = findViewById(R.id.back_button)

        //todo tabella punteggi utenti
        /*
        BackButton.setOnClickListener{
            val intent = Intent(this@ScoreActivity, HomeGameActivity::class.java)
            startActivity(intent)
        }

         */
    }
}