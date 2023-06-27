package it.uninsubria.catchstars

import android.Manifest
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.text.format.Time
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Chronometer
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.lang.Math.pow
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

class GameActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    private lateinit var Time: Chronometer
    private lateinit var ProgressBar: ProgressBar
    private lateinit var CatchButton: ImageButton
    private lateinit var SettingButton: ImageButton
    private lateinit var HomeGameButton: ImageButton
    private lateinit var ScoreButton: ImageButton

    private val entrance = LatLng(45.798328, 8.847318)
    private val church = LatLng(45.798281, 8.849082)
    private val dorm = LatLng(45.796906, 8.851511)
    private val mtg = LatLng(45.797838, 8.852398)
    private val cus = LatLng(45.796946, 8.853696)

    private var markerEntrance: Marker? = null
    private var markerChurch: Marker? = null
    private var markerDorm: Marker? = null
    private var markerMTG: Marker? = null
    private var markerCUS: Marker? = null

    private lateinit var Map: GoogleMap
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var permissionCode = 101

    //inizializzazione punteggio totale utente
    private var ptTot = 0

    //inizializzazione progress bar
    private var currentProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        Time = findViewById(R.id.time)
        CatchButton = findViewById(R.id.catch_button)
        ProgressBar = findViewById(R.id.progressbar)
        SettingButton = findViewById(R.id.setting)
        HomeGameButton = findViewById(R.id.home_back)
        ScoreButton = findViewById(R.id.score)

        //geolocalizzazione
        getCurrentLocationUser()

        /*
        //crash?
        val mapFragment = supportFragmentManager.findFragmentById(R.id.Map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        */


        Time.setTypeface(ResourcesCompat.getFont(this, R.font.aclonica)); //font cronometro

        //partenza cronometro
        Time.start()

        //inserimento posizione utente nella mappa e geolocalizzazione
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)



        /*
        *
        * //confronto distanza tra la posizione di tutti gli oggetti e la posizione degli utenti
        * -- assegnazione delle stelle ai punteggi delle stelle in base all'ordine
        * -- il punteggio dell'utente è inizialmente impostato a 0, quando un utente si avvicina a una stella,
        *    per catturarla deve premere sulla stella (onMarkerClick): si controlla la differenza della
        *    distanza tra utente e oggetto, se minore ai cinque metri, si legge il valore della stella e lo si
        *    somma al punteggio, la progressbar viene incrememntata di 1; se superiore ai 5m, makeText all'utente
        *    che deve avvicinarsi
        * ...
        * ...
        *
        * */

        //assegnazione posizione iniziale utente a una variabile
        val userLocation = getCurrentLocationUser()

        ProgressBar.setProgress(currentProgress)//imposta il valore iniziale della progressbar


        //todo metodi di fine livello
        //levelEnded() //chiama il metodo per la fine del livello e la comparsa del popup

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

    //add marker stars e posizione utente
    override fun onMapReady(googleMap: GoogleMap) {

        Map = googleMap

        //visualizzazione indicatori
        markerEntrance = googleMap.addMarker(
            MarkerOptions()
                .position(entrance)
                .title("30 pt")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.star_icon40)))
        Map.moveCamera(CameraUpdateFactory.newLatLng(entrance))
        markerEntrance?.tag = 30

        markerChurch = googleMap.addMarker(
            MarkerOptions()
                .position(church)
                .title("25 pt")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.star_icon40)))
        Map.moveCamera(CameraUpdateFactory.newLatLng(church))
        markerChurch?.tag = 25

        markerDorm = googleMap.addMarker(
            MarkerOptions()
                .position(dorm)
                .title("15 pt")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.star_icon40)))
        Map.moveCamera(CameraUpdateFactory.newLatLng(dorm))
        markerDorm?.tag = 15

        markerMTG = googleMap.addMarker(
            MarkerOptions()
                .position(mtg)
                .title("10 pt")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.star_icon40)))
        Map.moveCamera(CameraUpdateFactory.newLatLng(mtg))
        markerMTG?.tag = 10

        markerCUS = googleMap.addMarker(
            MarkerOptions()
                .position(cus)
                .title("20 pt")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.star_icon40)))
        Map.moveCamera(CameraUpdateFactory.newLatLng(cus))
        markerCUS?.tag = 20

        //marcatore posizione utente all'apertura dell'app (statico)
        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        val markerOptions = MarkerOptions()
            .position(latLng)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.character40))
        Map.animateCamera(CameraUpdateFactory.newLatLng(latLng))

        Map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17.0f))
        Map.addMarker(markerOptions)

        Map.setOnMarkerClickListener(this)
    }

    //geolocalizzazione in tempo reale
    private fun getCurrentLocationUser(){
        if(ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_COARSE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), permissionCode)
            return
        }

        //Map.isMyLocationEnabled = true //crash

        val getLocation = fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            location ->
            if(location != null){
                currentLocation = location
                /*
                //todo aggiornamento posizione utente in tempo reale -> crash

                val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)

                val markerOptions = MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.character40))
                Map.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                Map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17.0f))
                Map.addMarker(markerOptions)
                */

                val mapFragment = supportFragmentManager.findFragmentById(R.id.Map) as SupportMapFragment?
                mapFragment?.getMapAsync(this)
            }
        }
    }

    //permesso di geolocalizzazione
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
         when(requestCode){
             permissionCode -> if(grantResults.isNotEmpty() && grantResults[0]==
                     PackageManager.PERMISSION_GRANTED){
                 getCurrentLocationUser()
             }
         }
    }

    //todo override markerClick
    override fun onMarkerClick(marker: Marker): Boolean {

        //todo controllo della distanza tra oggetto e astronauta (<5m)
        /*
        val userLocation = getCurrentLocationUser() as LatLng
        val starLocation = marker.getPosition()

        val starLat = starLocation.latitude * 3.14/180
        val starLng = starLocation.longitude + 3.14/180

        val userLat = userLocation.latitude * 3.14/180
        val userLng = userLocation.longitude * 3.14/180

        val r = 6376.5 * 1000

        val x1 = r * cos(starLat) * cos(starLng)
        val y1 = r * cos(starLat) * sin(starLng)
        val x2 = r * cos(userLat) * cos(userLng)
        val y2 = r * cos(userLat) * cos(userLng)

        val distance = sqrt((x2 - x1).pow(0.0) + (y2-y1).pow(0.0))

        //DistanceCalculator() //possibile metodo per pulire il codice

        if(distance < 5){

            //todo calcolo punti
            val ptStar = marker.getTag() as Int //legge il valore del tag come intero
            ptTot += ptStar //somma dei punti con il punteggio della stella
        */

            //todo visibilità oggetto a false (lo rende invisibile) - non funziona
            marker.isVisible = false //nasconde la stellina

            //incremento progressbar
            currentProgress = currentProgress + 1
            ProgressBar.setProgress(currentProgress)
            ProgressBar.setMax(5) //imposta il valore massimo della progressbar

            //messaggio di cattura avvenuta
            Toast.makeText(this, "Congratulazioni! Hai catturato una stella!", Toast.LENGTH_LONG).show()
/*
        }else{
            Toast.makeText(this, "Sei ancora troppo lontano dalla stellina, avvicinati per raccoglierla!", Toast.LENGTH_LONG).show()
            }

 */

        return false
    }

    /*
    private fun DistanceCalculator(){

        val userLocation = getCurrentLocationUser() as LatLng
        val starLocation = marker.getPosition()

        val starLat = starLocation.latitude * 3.14/180
        val starLng = starLocation.longitude + 3.14/180

        val userLat = userLocation.latitude * 3.14/180
        val userLng = userLocation.longitude * 3.14/180

        val r = 6376.5 * 1000

        val x1 = r * cos(starLat) * cos(starLng)
        val y1 = r * cos(starLat) * sin(starLng)
        val x2 = r * cos(userLat) * cos(userLng)
        val y2 = r * cos(userLat) * cos(userLng)

        val distance = sqrt((x2 - x1).pow(0.0) + (y2-y1).pow(0.0))

     */

    //todo metodo conversione minuti in secondi
    private fun timeConverter(){
        /*gli viene passato il tempo registrato dal cronometro al momento del timeStop

        //conversione del tempo in formato MM:SS in secondi

        TimeTot = Time * 60

        return TimeTot;
         */
    }

    //todo metodo gestione cronometro e calcolo punti
    fun totalPoint(ptTot: Int) {
        /*
        gli viene passato il punteggio ottenuto dagli oggetti (ptObj)
        e il tempo convertito dal metodo TimeConverter() (TimeTot)

        //possibile codice:

        ptTot = pt
        val timeTot = timeConverter() (?)
        if (timeTot > 600) {
            val plusTime = timeTot - 600 //10min in secondi
            val finalPt = ptTot - (plusTime / 30)
            return finalPt


        //Il valore di finalPt viene inviato sia al popup sia registrato nel database
        */
    }

    //todo metodo raccolta oggetti
    fun levelEnded(){

        /*
        //quando i punti totali degli oggetti arrivano a 100, il livello termina e si avviano i metodi
        di calcolo punteggio
        if(ptObj == 100){
            Time.stop() //viene chiamato lo stop al raggiungimento dei 100 punti
            //chiamare metodo che converte i minuti in secondi

        totalPoint() //chiama il metodo di calcolo punti e gli viene passato come argomento il tempo
        totale registrato dal cronometro (int secondi) e i punti raccolti dal catch
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

private fun GoogleMap.setOnMyLocationClickListener(gameActivity: GameActivity) {
}

private fun GoogleMap.setOnMyLocationButtonClickListener(gameActivity: GameActivity) {
}
