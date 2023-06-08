package it.uninsubria.catchstars

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Chronometer
import androidx.appcompat.app.AppCompatActivity

class GameActivity (intent : Intent) : AppCompatActivity() {

    private lateinit var Time: Chronometer
    private lateinit var CatchButton: Button
    private lateinit var SettingButton: Button
    private lateinit var HomeGameButton: Button
    private lateinit var ScoreButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        Time = findViewById(R.id.time)
        CatchButton = findViewById(R.id.catch_button)
        SettingButton = findViewById(R.id.setting)
        HomeGameButton = findViewById(R.id.home_back)
        ScoreButton = findViewById(R.id.score)


        //partenza cronometro (?)
       Time.start()


        //todo gestione mappa e assegnazione oggetti
        /*
        * l'oggetto più lontano deve essere raggiungibile in meno di 10 minuti a piedi (raggio
        * massimo oggetti)
        *
        * assegnazione del proprio punteggio al singolo oggetto
        * controllare: inserimento casuale degli oggetti per ripetizione o singolo livello con
        * inserimento fisso degli oggetti?
        * */

        //todo pulsante catch
        /*
        punteggi stelle: 10,15,20,25,30
        raccoglie oggetti sulla mappa e aumenta i punti, raggiunto il punteggio massimo dagli
        oggetti viene chiamato il metodo levelEnded()
        */

        //todo progress bar
        //aumenta in modo uniforme di 20 punti su cento per ogni oggetto raccolto

        //pulsanti home, setting e score
        SettingButton.setOnClickListener{
            val intent = Intent(this@GameActivity, SettingActivity::class.java)
            startActivity(intent)
        }

        HomeGameButton.setOnClickListener{
            val intent = Intent(this@GameActivity, HomeGameActivity::class.java)
            startActivity(intent)
        }

        ScoreButton.setOnClickListener{
            val intent = Intent(this@GameActivity, ScoreActivity::class.java)
            startActivity(intent)
        }


    }

    //todo metodo termine tempo/raccolta oggetti
    fun levelEnded(){
    /*comparsa finestra (chiamata nuova activity?) di conclusione livello
    * textview di fine livello
    * punteggio totale
    * pulsanti setting, home e score
    *
    * vengono conteggiati i punti,
      inseriti nel database e registrati nella tabella nell'activity score
      * per ogni 30 secondi che superano i 10 minuti viene sottratto al
      * punteggio totale 1 pt
      *
    * */
    }

    //todo metodo gestione cronometro
    fun chronometer(){
        /*

        Time.stop()

        creare variabile "tempo totale" (tempo registrato dal chronometro alla chiamata dello stop()
        creare variabile "tempo aggiunto" ("tempo totale" - 10minuti)
        creare variabile "punteggio totale"
        se il "tempo rimanente" è superiore 10 minuti
            "punteggio totale(int)" - (tempo aggiunto(secondi) / 30 secondi)

     tempo per livello: 10 minuti

     partenza cronometro, quando scade il tempo viene chiamato il metodo levelEnded()
     */
    }


}