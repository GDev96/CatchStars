package it.uninsubria.catchstars

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.Chronometer
import android.widget.PopupWindow
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


        //partenza cronometro
        Time.start()

        /*
        if(ptObj == 100){
            Time.stop() //viene chiamato lo sto al raggiungimento dei 100 punti
            //chiamare metodo che converte i minuti in secondi

        */

        //todo gestione mappa e assegnazione oggetti
        /*
        * l'oggetto più lontano deve essere raggiungibile in meno di 10 minuti a piedi (raggio
        * massimo oggetti)
        *
        * assegnazione del proprio punteggio al singolo oggetto
        *
        * inserire il raggio di inserimento degli indicatori a 150m
        *
        * controllare: inserimento casuale degli oggetti per ripetizione o singolo livello con
        * inserimento fisso degli oggetti?
        * */

        //todo pulsante catch
        /*
        punteggi stelle: 10,15,20,25,30
        raccoglie oggetti sulla mappa e aumenta i punti

        onClick: legge il valore assegnato all'oggetto e lo somma al punteggio

        totalPoint() //chiama il metodo di calcolo punti e gli viene passato come argomento il tempo
        totale registrato dal cronometro (int secondi) e i punti raccolti dal catch

        levelEnded() //chiama il metodo per la fine del livello e la comparsa del popup
        */

        //todo progress bar
        //aumenta in modo uniforme di 20 punti su cento per ogni oggetto raccolto

        //pulsanti home, setting e score
        SettingButton.setOnClickListener{
            val intent = Intent(this@GameActivity, SettingActivity::class.java)
            startActivity(intent)
        }

        HomeGameButton.setOnClickListener{
            secureExit()
        }

        ScoreButton.setOnClickListener{
            val intent = Intent(this@GameActivity, ScoreActivity::class.java)
            startActivity(intent)
        }
    }

    //todo metodo conversione minuti in secondi
    private fun timeConverter(){
        /*gli viene passato il tempo registrato dal cronometro

        //conversione del tempo in formato MM:SS in secondi
        TimeTot = Time * 60

        return TimeTot;
         */
    }

    //todo metodo gestione cronometro e calcolo punti
    fun totalPoint() {
        /*
        gli viene passato il punteggio ottenuto dagli oggetti (ptObj)
        e il tempo convertito dal metodo TimeConverter() (TimeTot)

        //possibile codice:
        var ptObj: Int
        var ptTot: Int
        ptTot = ptObj
        val timeTot = timeConverter() (?)
        if (timeTot > 600) {
            val plusTime = timeTot - 600 //10min in secondi
            val finalPt = ptTot - (plusTime / 30)
            return finalPt


        //Il valore di finalPt viene inviato sia al popup sia registrato nel database
        */
    }

    //todo metodo termine tempo/raccolta oggetti
    fun levelEnded(){

        //comparsa popup di fine livello
        //levelEndedPopup()

        //todo metodo popup fine livello (vedi inflater)
        /*fun levelEndedPopup(){
            //inflate al layout del popup
            val inflater = ViewInflater.from(view).inflate(R.layout.popup_levelend, null)

            //creazione della finestra popup
            val width = inflater.width()
            val height = inflater.height()
            val focusable = true

            // lets taps outside the popup also dismiss it
            final PopupWindow LevelEnded = PopupWindow(popupView, width, height, focusable)

            //mostra il popup posizionandolo al centro
            popupWindow.show(view, Gravity.CENTER, 0, 0)

            // fa scomparire il popup quando si tocca la parte esterna della finestra (non necessario)
            popupView.setOnTouchListener {
                // onTouchListener is called when the user touches the popup window
                // if the user taps outside of the popup window, dismiss it
                if (event.target.isView() && !focusable) {
                    popupWindow.dismiss()
                }
            }
        }
          https://stackoverflow.com/questions/5944987/how-to-create-a-popup-window-popupwindow-in-android
        */
    }

    //metodo uscita sicura
    private fun secureExit() {
        val builder = AlertDialog.Builder(this@GameActivity)
        builder.setTitle("Vuoi abbandonare la missione?")

        builder.setPositiveButton("Missione annullata"){ dialog, which ->
            val intent = Intent(this@GameActivity, HomeGameActivity::class.java)
            startActivity(intent)
        }

        builder.setNegativeButton("Continua la missione", null)

        val dialog = builder.create()
        dialog.show()
    }
}