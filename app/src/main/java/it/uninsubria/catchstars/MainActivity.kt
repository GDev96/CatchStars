package it.uninsubria.catchstars

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var startImageButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //assegnazione alla variabile dell'oggetto (button)
        startImageButton = findViewById(R.id.Start_button)


        //assegnazione(chiamata) del metodo al button
        startImageButton.setOnClickListener{
            goLoginPage()
        }

        startMusic()
    }

    //metodo per il passaggio alla schermata di Login
    fun goLoginPage() {
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
    }

    //metodo per avvio service musica in background
    private fun startMusic() {
        val intent = Intent(this@MainActivity, BackgroundMusic::class.java)
        startService(intent)
    }
}
