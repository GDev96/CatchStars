package it.uninsubria.catchstars

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var startImageButton: ImageButton //dichiarazione di una variabile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //assegnazione alla variabile dell'oggetto (button)
        startImageButton = findViewById(R.id.Start_button)

        //assegnazione(chiamata) del metodo al button
        startImageButton.setOnClickListener{
            goLoginPage()
        }

        //todo creazione e avvio service musica in sottofondo e suoni
    }

    //metodo per il passaggio alla schermata di Login
    fun goLoginPage() {
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}
