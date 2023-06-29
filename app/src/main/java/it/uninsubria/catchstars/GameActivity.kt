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
import androidx.loader.content.AsyncTaskLoader
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.LatLng
import java.lang.Math.pow
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

class GameActivity : AppCompatActivity(), OnMapReadyCallback, OnMarkerClickListener {

    private lateinit var Time: Chronometer
    private lateinit var ProgressBar: ProgressBar
    //private lateinit var CatchButton: ImageButton
    private lateinit var SettingButton: ImageButton
    private lateinit var HomeGameButton: ImageButton
    private lateinit var ScoreButton: ImageButton

    private val entrance = LatLng(45.798328, 8.847318)
    private val church = LatLng(45.798281, 8.849082)
    private val dorm = LatLng(45.796906, 8.851511)
    private val mtg = LatLng(45.797838, 8.852398)
    private val cus = LatLng(45.796946, 8.853696)
    private val albero = LatLng(45.825538, 8.849489)

    private var markerEntrance: Marker? = null
    private var markerChurch: Marker? = null
    private var markerDorm: Marker? = null
    private var markerMTG: Marker? = null
    private var markerCUS: Marker? = null
    private var markerAlbero: Marker? = null

    private var userMarker: Marker? = null

    private lateinit var Map: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101

    //inizializzazione punteggio totale utente
    private var ptTot = 0

    //inizializzazione progress bar
    private var currentProgress = 0

    companion object {
        //
        private const val DEFAULT_ZOOM = 15
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

        // Keys for storing activity state.
        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"

        // Used for selecting the current place.
        private const val M_MAX_ENTRIES = 5
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        Time = findViewById(R.id.time)
        //CatchButton = findViewById(R.id.catch_button)
        ProgressBar = findViewById(R.id.progressbar)
        SettingButton = findViewById(R.id.setting)
        HomeGameButton = findViewById(R.id.home_back)
        ScoreButton = findViewById(R.id.score)

        //ci deve essere per forza
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        Time.setTypeface(ResourcesCompat.getFont(this, R.font.aclonica)); //font cronometro

        //partenza cronometro
        Time.start()

        //inserimento posizione utente nella mappa e geolocalizzazione
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        /*
        * -- il punteggio dell'utente è inizialmente impostato a 0, quando un utente si avvicina a una stella (<5m),
        *    per catturarla deve premere sulla stella (onMarkerClick): si controlla la differenza della
        *    distanza tra utente e oggetto, si legge il valore della stella e lo si
        *    somma al punteggio, la progressbar viene incrememntata di 1; se superiore ai 5m, makeText all'utente
        *    che deve avvicinarsi
        * ...
        * ...
        *
        * */

        //assegnazione posizione iniziale utente a una variabile
        //val userLocation = getCurrentLocationUser()

        ProgressBar.setProgress(currentProgress)//imposta il valore iniziale della progressbar

        //todo metodi di fine livello
        //if(ptTot == 100)
        //  levelEnded() //chiama il metodo per la fine del livello e la comparsa del popup

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


    //add marker stars e posizione utente statica
    override fun onMapReady(googleMap: GoogleMap) {

        Map = googleMap

        Map.setMaxZoomPreference(18.0f) // Imposta il valore massimo dello zoom
        Map.setMinZoomPreference(10.0f) // Imposta il valore minimo dello zoom

        //visualizzazione indicatori
        markerEntrance = Map.addMarker(
            MarkerOptions()
                .position(entrance)
                .title("30 pt")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.star_icon40)))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(entrance))
        markerEntrance?.tag = 30

        markerChurch = Map.addMarker(
            MarkerOptions()
                .position(church)
                .title("25 pt")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.star_icon40)))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(church))
        markerChurch?.tag = 25

        markerDorm = Map.addMarker(
            MarkerOptions()
                .position(dorm)
                .title("15 pt")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.star_icon40)))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(dorm))
        markerDorm?.tag = 15

        markerMTG = Map.addMarker(
            MarkerOptions()
                .position(mtg)
                .title("10 pt")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.star_icon40)))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(mtg))
        markerMTG?.tag = 10

        markerCUS = Map.addMarker(
            MarkerOptions()
                .position(cus)
                .title("20 pt")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.star_icon40)))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cus))
        markerCUS?.tag = 20

        markerAlbero = Map.addMarker(
            MarkerOptions()
                .position(albero)
                .title("5 pt")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.star_icon40)))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cus))
        markerAlbero?.tag = 5

        Map.setOnMarkerClickListener(this)

        getCurrentLocationUser()

    }


    //geolocalizzazione in tempo reale
    private fun getCurrentLocationUser() { //callback: (LatLng?) -> Unit
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                permissionCode
            )
            /*
            callback(null)
             */

        } else {
            Map.isMyLocationEnabled = true

            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                    location ->
                if (location != null) {
                    val latLng = LatLng(location.latitude, location.longitude)

                    Map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))

                    //creazione marker utente, viene aggiornata la posizione solo premendo sulla mappa
                    if (userMarker != null) {
                        userMarker!!.remove()
                    }

                    val markerOptions = MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.character40))
                    userMarker = Map.addMarker(markerOptions)


                    /*
                }else{
                    callback(null)
                }
                 */

                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Location permissions granted, start location updates
                getCurrentLocationUser()
                /*
                { userLocation ->
                    if (userLocation != null) {
                        // User location is available, do something with it
                    } else {
                        // User location is not available, handle the case
                        Toast.makeText(this, "Impossibile ottenere la posizione dell'utente", Toast.LENGTH_LONG).show()
                    }
                }

                 */
            }
        }
    }

    //todo markerClick

    override fun onMarkerClick(marker: Marker): Boolean {

            val userLocation = userMarker?.position
            val starLocation = marker.position

            val userLat = Math.toRadians(userLocation?.latitude as Double)
            val userLng = Math.toRadians(userLocation.longitude as Double)
            val starLat = Math.toRadians(starLocation.latitude)
            val starLng = Math.toRadians(starLocation.longitude)

            val earthRadius = 6371.0 // Earth's radius in kilometers

            val latDistance = starLat - userLat
            val lngDistance = starLng - userLng

            val a = sin(latDistance / 2) * sin(latDistance / 2) +
                    cos(Math.toRadians(userLat)) * cos(Math.toRadians(starLat)) *
                    sin(lngDistance / 2) * sin(lngDistance / 2)

            val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

            val distance = earthRadius * c * 1000 // Distance in meters

            if (distance < 2) {
                val tag = marker.tag
                if (tag != null) {
                    val ptStar = tag.toString().toIntOrNull()
                    if (ptStar != null) {
                        ptTot += ptStar

                        marker.isVisible = false // Hide the marker

                        //Increment progressbar
                        currentProgress++
                        ProgressBar.progress = currentProgress
                        ProgressBar.max = 5 // Set the maximum value of the progressbar

                        val longMessCongrat = "Congratulazioni! Hai catturato una stella! \nIl tuo punteggio è: $ptTot"
                        Toast.makeText(this, longMessCongrat, Toast.LENGTH_LONG).show()

                    } else {
                        Toast.makeText(this, "Il valore del tag non può essere convertito in un intero", Toast.LENGTH_LONG).show()

                    }
                } else {
                    Toast.makeText(this, "Errore nella cattura! Riprova!", Toast.LENGTH_LONG).show()
                }





            } else {
                marker.title //todo da controllare
                val missingDistance = (distance - 2).toInt()
                val longMessDistance = "Ti mancano $missingDistance metri, \navvicinati per raccogliere la stellina!"
                Toast.makeText(
                    this,
                    longMessDistance,
                    Toast.LENGTH_LONG
                ).show()
            }
        return true
    }


    //todo metodo raccolta oggetti
    fun levelEnded(){

        /*
        //quando i punti totali degli oggetti arrivano a 100, il livello termina e si avviano i metodi
        di calcolo punteggio
        if(ptObj == 100){
            Time.stop() //viene chiamato lo stop al raggiungimento dei 100 punti
            timeconverter(Time) //chiama metodo che converte i minuti in secondi

        totalPoint() //chiama il metodo di calcolo punti e gli viene passato come argomento il tempo
        totale registrato dal cronometro (int secondi) e i punti raccolti dal catch
        */
    }

    //todo metodo conversione minuti in secondi
    private fun timeConverter(){

        /*
        conversione del tempo ottenuto dal cronometro in secondi
        import java.time.Duration

        fun main() {
            val duration = Duration.ofMinutes(2).plusSeconds(30) // Esempio di durata di 2 minuti e 30 secondi

            val seconds = duration.toSeconds()
            val secondsInt = seconds.toInt()

            println("Tempo in secondi: $secondsInt")
        }*/


        /*gli viene passato il tempo registrato dal cronometro al momento del timeStop

        //conversione del tempo in formato MM:SS in secondi
        timeTot = Time * 60 //as Int

        return timeTot;
         */
    }

    //todo metodo gestione cronometro e calcolo punti
    fun totalPoint(ptTot: Int) {
        /*
        gli viene passato il punteggio ottenuto dagli oggetti (ptObj)
        e il tempo convertito dal metodo TimeConverter() (TimeTot)

        //possibile codice:

        val timeTot = timeConverter() (?)
        if (timeTot > 600) {
            val plusTime = timeTot - 600 //10min in secondi
            val finalPt = ptTot - (plusTime / 30)
            return finalPt


        //Il valore di finalPt viene inviato sia al popup sia registrato nel database
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
