package it.uninsubria.catchstars

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Chronometer
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class GameActivity : AppCompatActivity(), OnMapReadyCallback{

    private lateinit var Time: Chronometer
    //private lateinit var MapView: SupportMapFragment
    private lateinit var googleMap: GoogleMap
    private lateinit var CatchButton: ImageButton
    private lateinit var SettingButton: ImageButton
    private lateinit var HomeGameButton: ImageButton
    private lateinit var ScoreButton: ImageButton

    /*marker
    val entrance = LatLng(45.798328, 8.847318)
    val church = LatLng(45.798281, 8.849082)
    val dorm = LatLng(45.796906, 8.851511)
    val mtg = LatLng(45.797838, 8.852398)
    val cus = LatLng(45.796946, 8.853696)
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        Time = findViewById(R.id.time)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.Map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

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

        //onMapReady(googleMap) --> non serve

        //todo inserimento posizione utente nella mappa e geolocalizzazione
        //todo assegnazione punti ai puntatori in base alla posizione iniziale dell'utente
            //https://developers.google.com/maps/documentation/android-sdk/marker?hl=it#maps_android_markers_add_a_marker-kotlin
            //https://developers.google.com/maps/documentation/android-sdk/location?hl=it
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

    //add marker stars
    override fun onMapReady(googleMap: GoogleMap) {
        // Add a marker
        // and move the map's camera to the same location.
        val entrance = LatLng(45.798328, 8.847318)
        googleMap.addMarker(
            MarkerOptions()
                .position(entrance)
                .title("Entrance campus")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.star_icon40)))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(entrance))

        val church = LatLng(45.798281, 8.849082)
        googleMap.addMarker(
            MarkerOptions()
                .position(church)
                .title("Church")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.star_icon40)))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(church))

        val dorm = LatLng(45.796906, 8.851511)
        googleMap.addMarker(
            MarkerOptions()
                .position(dorm)
                .title("Dorm")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.star_icon40)))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(dorm))

        val mtg = LatLng(45.797838, 8.852398)
        googleMap.addMarker(
            MarkerOptions()
                .position(mtg)
                .title("MTG")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.star_icon40)))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(mtg))

        val cus = LatLng(45.796946, 8.853696)
        googleMap.addMarker(
            MarkerOptions()
                .position(cus)
                .title("CUS Insubria")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.star_icon40)))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cus))
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

        //finestra act di fine livello
        //levelEndedPopup()
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