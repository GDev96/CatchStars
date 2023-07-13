package it.uninsubria.catchstars

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SettingActivity : AppCompatActivity() {

    private lateinit var MusicOnButton: ImageButton
    private lateinit var MusicOffButton: ImageButton
    private lateinit var InfoButton: ImageButton
    private lateinit var FAQButton: ImageButton
    private lateinit var BackButton: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        //associazione variabili
        MusicOnButton = findViewById(R.id.musicOn_button)
        MusicOffButton = findViewById(R.id.musicOff_button)
        InfoButton = findViewById(R.id.info_button)
        FAQButton = findViewById(R.id.faq_button)
        BackButton = findViewById(R.id.back_button)

        //tasti musica -> gestione musica in background
        // attivazione service musica in background
        MusicOnButton.setOnClickListener{
            Toast.makeText(this, "Music: Start", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@SettingActivity, BackgroundMusic::class.java)
            startService(intent)
        }

        //disattivazione service musica in background
        MusicOffButton.setOnClickListener{
            Toast.makeText(this, "Music: Stop", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@SettingActivity, BackgroundMusic::class.java)
            stopService(intent)
        }

        //info
        InfoButton.setOnClickListener{
            val intent = Intent( this@SettingActivity, InfoActivity::class.java)
            startActivity(intent)
        }

        //FAQ
        FAQButton.setOnClickListener {
            val intent = Intent(this@SettingActivity, FAQActivity::class.java)
            startActivity(intent)
        }

        //back button
        BackButton.setOnClickListener{
            val intent = Intent(this@SettingActivity, HomeGameActivity::class.java)
            startActivity(intent)
        }


    }

}

