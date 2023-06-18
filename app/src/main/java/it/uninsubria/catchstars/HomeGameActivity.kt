package it.uninsubria.catchstars

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeGameActivity : AppCompatActivity() {

    private lateinit var playButton: ImageButton
    private lateinit var settingButton: ImageButton
    private lateinit var scoreButton: ImageButton
    private lateinit var logOutButton: ImageButton
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homegame_activity)

        auth = Firebase.auth

        playButton = findViewById(R.id.playgame)
        settingButton = findViewById(R.id.setting)
        scoreButton = findViewById(R.id.score)
        logOutButton = findViewById(R.id.exit)


        //button play
        playButton.setOnClickListener {
            val intent = Intent(this@HomeGameActivity, RulesActivity::class.java)
            startActivity(intent)
        }

        //button setting
        settingButton.setOnClickListener{
            val intent = Intent (this@HomeGameActivity, SettingActivity::class.java)
            startActivity(intent)
        }


        //button score
        scoreButton.setOnClickListener{
            val intent = Intent (this@HomeGameActivity, ScoreActivity::class.java)
            startActivity(intent)
        }


        //exitbutton -> logout firebase e ritorno a schermata di avvio
        logOutButton.setOnClickListener{
            //logout firebase
            Firebase.auth.signOut()
            val intent = Intent(this@HomeGameActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}






