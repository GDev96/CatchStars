package it.uninsubria.catchstars

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeGameActivity(intent: Intent) : AppCompatActivity() {

    private lateinit var PlayButton: Button
    private lateinit var SettingButton: Button
    private lateinit var ScoreButton: Button
    private lateinit var LogOutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homegame_activity)

        //associazione button/variabili
        PlayButton = findViewById(R.id.playgame)
        SettingButton = findViewById(R.id.setting)
        ScoreButton = findViewById(R.id.score)
        LogOutButton = findViewById(R.id.exit)

        //button play
        PlayButton.setOnClickListener {
            val intent = Intent(this@HomeGameActivity, GameActivity::class.java)
            startActivity(intent)
        }

        //button setting
        SettingButton.setOnClickListener{
            val intent = Intent (this@HomeGameActivity, SettingActivity::class.java)
            startActivity(intent)
        }

        //button score
        ScoreButton.setOnClickListener{
            val intent = Intent (this@HomeGameActivity, ScoreActivity::class.java)
            startActivity(intent)
        }

        //exitbutton -> logout firebase e ritorno a schermata di avvio
        LogOutButton.setOnClickListener{
            //logout firebase
            Firebase.auth.signOut()
            //creazione pop up(?) "sei sicuro di voler uscire?" due pulsanti: exit -> mainactivity, back -> homegameactivity
            val intent = Intent(this@HomeGameActivity, MainActivity::class.java)
            startActivity(intent)
        }



    }


}







