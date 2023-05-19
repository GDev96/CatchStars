package it.uninsubria.catchstars

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var startImageButton: ImageButton //dichiarazione di una variabile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startImageButton = findViewById(R.id.Start_button) //assegnazione alla variabile dell'oggetto (pulsante)

    }

    fun goLoginPage(v: View) {  //metodo per il passaggio alla schermata di Login
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
    }

    //assegnazione del metodo al pulsante?

}
