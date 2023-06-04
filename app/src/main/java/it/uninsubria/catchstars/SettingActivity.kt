package it.uninsubria.catchstars

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SettingActivity (intent: Intent) : AppCompatActivity() {

    private lateinit var VolOnButton: Button
    private lateinit var VolOffButton: Button
    private lateinit var MusicOnButton: Button
    private lateinit var MusicOffButton: Button
    private lateinit var InfoButton: Button
    private lateinit var BackButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        //associazione variabili
        VolOnButton = findViewById(R.id.volumeOn_button)
        VolOffButton = findViewById(R.id.volumeOff_button)
        MusicOnButton = findViewById(R.id.musicOn_button)
        MusicOffButton = findViewById(R.id.musicOff_button)
        InfoButton = findViewById(R.id.info_button)
        BackButton = findViewById(R.id.back_button)

        //tasti volume -> gestione suoni tasti
        VolOnButton.setOnClickListener{
            //todo attivazione servizio suoni tasti
        }

        VolOffButton.setOnClickListener{
            //todo interruzione service suoni tasti
        }

        //tasti musica --> gestione musica in sottofondo
        MusicOnButton.setOnClickListener{
            //todo attivazione musica in sottofondo
            //metodo MusicOn?
        }

        MusicOffButton.setOnClickListener{
            //todo disattivazione musica in sottofondo
        }

        //info --> popup informazioni
        InfoButton.setOnClickListener{
            Toast.makeText(applicationContext, "Progetto: CatchStars! - Versione 1.0 - Data di rilascio: 06/2023 - Sviluppato da Frattini Gaia MT 736610", Toast.LENGTH_LONG).show()
        }

        //back button
        BackButton.setOnClickListener{
            val intent = Intent(this@SettingActivity, HomeGameActivity::class.java)
            startActivity(intent)
        }
    }


}
/*
nomi metodi layout:
VolumeOn
VolumeOff
MusicOn
MusicOff
goHomeGamePage
PopUpAbout
 */
