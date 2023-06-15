package it.uninsubria.catchstars

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeGameActivity : AppCompatActivity() {

    private lateinit var playButton: Button
    private lateinit var settingButton: Button
    private lateinit var scoreButton: Button
    private lateinit var logOutButton: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homegame_activity)

        auth = Firebase.auth

        //fin qui funziona

        //Con questo codice, l'app va in crash e torna alla main activity o si chiude completamente
/*
        playButton = findViewById(R.id.playgame)
        settingButton = findViewById(R.id.setting)
        scoreButton = findViewById(R.id.score)
        logOutButton = findViewById(R.id.exit)
*/
/*
        //button play
        playButton.setOnClickListener {
            val intent = Intent(this@HomeGameActivity, GameActivity::class.java)
            startActivity(intent)
        }
*/
        /*
        //button setting
        settingButton.setOnClickListener{
            val intent = Intent (this@HomeGameActivity, SettingActivity::class.java)
            startActivity(intent)
        }
        */
/*
        //button score
        scoreButton.setOnClickListener{
            val intent = Intent (this@HomeGameActivity, ScoreActivity::class.java)
            startActivity(intent)
        }
*/
/*
        //exitbutton -> logout firebase e ritorno a schermata di avvio
        logOutButton.setOnClickListener{
            //logout firebase
            Firebase.auth.signOut()
            val intent = Intent(this@HomeGameActivity, MainActivity::class.java)
            startActivity(intent)
        }
*/
    }

}






