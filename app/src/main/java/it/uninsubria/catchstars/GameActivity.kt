package it.uninsubria.catchstars

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.widget.Chronometer
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.LatLng
import kotlin.math.*

class GameActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var Time: Chronometer
    private lateinit var ProgressBar: ProgressBar
    private lateinit var SettingButton: ImageButton
    private lateinit var HomeGameButton: ImageButton
    private lateinit var ScoreButton: ImageButton

    private val entrance = LatLng(45.798328, 8.847318)
    private val church = LatLng(45.798281, 8.849082)
    private val dorm = LatLng(45.796906, 8.851511)
    private val mtg = LatLng(45.797838, 8.852398)
    private val cus = LatLng(45.796946, 8.853696)
    private val magnolia = LatLng(45.824752, 8.850037)
    private val pilastro = LatLng(45.825777, 8.849119)
    private val faggio = LatLng(45.825531, 8.849522)

    private var markerEntrance: Marker? = null
    private var markerChurch: Marker? = null
    private var markerDorm: Marker? = null
    private var markerMTG: Marker? = null
    private var markerCUS: Marker? = null
    private var markerMagnolia: Marker? = null
    private var markerPilastro: Marker? = null
    private var markerFaggio: Marker? = null

    private var userMarker: Marker? = null

    //punteggi stelle
    private val ptStar1 = 5
    private val ptStar2 = 10
    private val ptStar3 = 15
    private val ptStar4 = 20
    private val ptStar5 = 25

    private val pil = 60
    private val fag = 40
    private val mag = 40

    private lateinit var Map: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101
    private var currentUserLocation: LatLng? = null

    private var ptTot = 0

    private var currentProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        Time = findViewById(R.id.time)
        ProgressBar = findViewById(R.id.progressbar)
        SettingButton = findViewById(R.id.setting)
        HomeGameButton = findViewById(R.id.home_back)
        ScoreButton = findViewById(R.id.score)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        Time.setTypeface(ResourcesCompat.getFont(this, R.font.aclonica)); //font cronometro
        Time.start()

        //inserimento posizione utente nella mappa e geolocalizzazione
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        ProgressBar.setProgress(currentProgress)//imposta il valore iniziale della progressbar

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

        //regolazione dello zoom della mappa
        Map.setMaxZoomPreference(25.0f)
        Map.setMinZoomPreference(14.0f)

        //visualizzazione indicatori
        markerEntrance = Map.addMarker(
            MarkerOptions()
                .position(entrance)
                .title("30 pt")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.star_icon40)))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(entrance))
        markerEntrance?.tag = ptStar5//30 punti

        markerChurch = Map.addMarker(
            MarkerOptions()
                .position(church)
                .title("25 pt")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.star_icon40)))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(church))
        markerChurch?.tag = ptStar4 //25 punti

        markerDorm = Map.addMarker(
            MarkerOptions()
                .position(dorm)
                .title("15 pt")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.star_icon40)))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(dorm))
        markerDorm?.tag = ptStar2//15 punti

        markerMTG = Map.addMarker(
            MarkerOptions()
                .position(mtg)
                .title("10 pt")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.star_icon40)))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(mtg))
        markerMTG?.tag = ptStar1 //10 punti

        markerCUS = Map.addMarker(
            MarkerOptions()
                .position(cus)
                .title("20 pt")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.star_icon40)))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cus))
        markerCUS?.tag = ptStar3 //20 punti

        //marker di testing
        markerPilastro = Map.addMarker(
            MarkerOptions()
                .position(pilastro)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.star_icon40)))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cus))
        markerPilastro?.tag = pil//60 punti

        markerMagnolia = Map.addMarker(
            MarkerOptions()
                .position(magnolia)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.star_icon40)))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cus))
        markerMagnolia?.tag = mag //40 punti

        markerFaggio = Map.addMarker(
            MarkerOptions()
                .position(faggio)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.star_icon40)))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cus))
        markerFaggio?.tag = fag //40 punti

        Map.setOnMarkerClickListener(this)

        getCurrentLocationUser()
    }

    //geolocalizzazione in tempo reale
    private fun getCurrentLocationUser() {
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
        } else {
            Map.isMyLocationEnabled = true

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val latLng = LatLng(location.latitude, location.longitude)

                    currentUserLocation = latLng

                    Map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))

                    /*
                    if (userMarker != null) {
                        userMarker!!.remove()
                    }

                    //creazione marker utente
                    val markerOptions = MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.character40))
                    userMarker = Map.addMarker(markerOptions)

                    userMarker?.position = latLng

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
                // Accesso alla posizione, avvia l'aggiornamento della posizione
                getCurrentLocationUser()
            }
        }
    }

    // Funzione per calcolare la distanza tra due posizioni LatLng
    private fun calculateDistance(user: LatLng, star: LatLng): Float {
        val results = FloatArray(1)
        Location.distanceBetween(
            user.latitude, user.longitude,
            star.latitude, star.longitude,
            results
        )
        return results[0]
    }

    //markerClick //funziona
    override fun onMarkerClick(marker: Marker): Boolean {

        //aggiorna la posizione dell'utente
        getCurrentLocationUser()
        // Verifica se la posizione utente corrente è disponibile
        val userLocation = currentUserLocation
        if (userLocation != null) {
            val starLocation = marker.position

            // Calcola la distanza tra il marker e la posizione utente
            val distance = calculateDistance(starLocation, userLocation)

            if (distance < 5) {
                val ptStar = marker.tag as? Int
                if (ptStar != null) {
                    ptTot += ptStar

                    marker.isVisible = false // nasconde la stellina

                    //Incremento progress bar
                    currentProgress = currentProgress + ptTot
                    ProgressBar.progress = currentProgress
                    ProgressBar.max = 100 //valore massimo della progress bar

                    val longMessCongrat =
                        "Hai catturato una stella! \nIl tuo punteggio è: $ptTot"
                    Toast.makeText(this, longMessCongrat, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        this,
                        "Ops, cattura non riuscita. Riprova!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                val missingDistance = (distance - 5).toInt()
                val longMessDistance = "Ti mancano $missingDistance metri alla stellina!"
                Toast.makeText(
                    this,
                    longMessDistance,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        //todo fine livello - da controllare
        if(ptTot == 100)
            levelEnded()

        return true
    }


    //todo metodo fine livello - da controllare
    fun levelEnded(){

        Time.stop() //ferma il cronometro

        // Conversione dei minuti in secondi
        val timeInSecond = timeConverter(Time)

        // Calcolo finale punti
        val finalPt = totalPoint(timeInSecond)

        //apertura schermata di fine livello
        val intent = Intent(this@GameActivity, LevelEnded::class.java)
        intent.putExtra("Punteggio finale:", finalPt)
        startActivity(intent)
    }

    //todo metodo conversione minuti in secondi - da controllare
    private fun timeConverter(Time: Chronometer): Int{

        val timeInMillis = SystemClock.elapsedRealtime() - Time.base
        val timeInSecond = timeInMillis / 1000

        return timeInSecond.toInt()
    }

    //todo metodo gestione cronometro e calcolo punti - da controllare
    fun totalPoint(timeInSecond: Int): Int {

        var finalPt = ptTot

        if(timeInSecond < 600) {
            val plusTime = timeInSecond - 600 //10min in secondi
            finalPt = ptTot - (plusTime / 30)
        }

        return finalPt
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
