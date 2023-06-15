package it.uninsubria.catchstars

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SettingActivity : AppCompatActivity() {

    private lateinit var VolOnButton: Button
    private lateinit var VolOffButton: Button
    private lateinit var MusicOnButton: Button
    private lateinit var MusicOffButton: Button
    private lateinit var InfoButton: Button
    private lateinit var FAQButton: Button
    private lateinit var BackButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)
/*
        //associazione variabili
        VolOnButton = findViewById(R.id.volumeOn_button)
        VolOffButton = findViewById(R.id.volumeOff_button)
        MusicOnButton = findViewById(R.id.musicOn_button)
        MusicOffButton = findViewById(R.id.musicOff_button)
        InfoButton = findViewById(R.id.info_button)
        FAQButton = findViewById(R.id.faq_button)
        BackButton = findViewById(R.id.back_button)

        //tasti volume -> gestione suoni tasti
        VolOnButton.setOnClickListener{
            //to do attivazione servizio suoni tasti (in forse)
        }

        VolOffButton.setOnClickListener{
            //to do interruzione service suoni tasti (in forse)
        }

        //tasti musica -> gestione musica in background
        // attivazione service musice in background
        MusicOnButton.setOnClickListener{
            val intent = Intent(this@SettingActivity, BackgroundMusic::class.java)
            startService(intent)
        }

        //disattivazione service musica in background
        MusicOffButton.setOnClickListener{
            val intent = Intent(this@SettingActivity, BackgroundMusic::class.java)
            startService(intent)
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

 */
    }

}
/*
nomi metodi layout:
VolumeOn
VolumeOff --> Volume.kt
 */
