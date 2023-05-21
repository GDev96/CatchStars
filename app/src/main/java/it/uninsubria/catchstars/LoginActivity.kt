package it.uninsubria.catchstars

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {        //creazione dell'activity con collegamento all'activity 'login'
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
    }
}