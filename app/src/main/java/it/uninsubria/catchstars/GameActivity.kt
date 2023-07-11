package it.uninsubria.catchstars

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
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
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.LatLng
import it.uninsubria.catchstars.database.DataBaseHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

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
    private val parco = LatLng(45.824133, 8.850831)
    private val campo = LatLng(45.820610, 8.853626)
    private val golf = LatLng(45.817590, 8.858858)
    private val bosco = LatLng(45.817503, 8.857845)

    private var markerEntrance: Marker? = null
    private var markerChurch: Marker? = null
    private var markerDorm: Marker? = null
    private var markerMTG: Marker? = null
    private var markerCUS: Marker? = null
    private var markerMagnolia: Marker? = null
    private var markerPilastro: Marker? = null
    private var markerFaggio: Marker? = null
    private var markerParco: Marker? = null
    private var markerCampo: Marker? = null
    private var markerGolf: Marker? = null
    private var markerBosco: Marker? = null

    //private var userMarker: Marker? = null

    //punteggi stelle
    private val ptStar1 = 5
    private val ptStar2 = 10
    private val ptStar3 = 15
    private val ptStar4 = 20
    private val ptStar5 = 25

    private val pil = 60
    private val fag = 40
    private val mag = 100

    private lateinit var Map: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101
    private var currentUserLocation: LatLng? = null

    private var ptTot = 0
    private var currentProgress = 0

    lateinit var db: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        Time = findViewById(R.id.time)
        ProgressBar = findViewById(R.id.progressbar)
        SettingButton = findViewById(R.id.setting)
        HomeGameButton = findViewById(R.id.home_back)
        ScoreButton = findViewById(R.id.score)

        //creazione della mappa
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //collegamento con il database
        db = DataBaseHelper(this)

        Time.setTypeface(ResourcesCompat.getFont(this, R.font.aclonica)); //font cronometro
        Time.start()

        //inserimento posizione utente nella mappa e geolocalizzazione
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        ProgressBar.setProgress(currentProgress)//imposta il valore iniziale della progressbar

        //salvataggio della data nel database
        val dataSystem = Calendar.getInstance().time //recupera la data dal sistema
        val data = SimpleDateFormat("dd-MM-yy", Locale.getDefault()).format(dataSystem)

        db.saveDate(data)

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
        Map.setMaxZoomPreference(20.0f)
        Map.setMinZoomPreference(12.0f)

        //todo sostituire i marker fissi con 10 marker con posizioni casuali senza punteggio assegnato

        getCurrentLocationUser()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val userLocation = LatLng(location.latitude, location.longitude)

                // Genera 10 marker casuali all'interno di un raggio di 150 metri dalla posizione dell'utente
                val radiusInMeters = 150.0
                val random = Random()

                for (i in 0 until 10) {

                    val randomAngle = random.nextDouble() * 2 * Math.PI //angolo casuale in radianti
                    val randomDistance = random.nextDouble() * radiusInMeters //distanza casuale

                    // Calcola le coordinate casuali
                    val randomLatLng = calculateLatLng(userLocation, randomDistance, randomAngle)

                    // Aggiungi il marker alla mappa utilizzando le coordinate casuali
                    val markerOptions = MarkerOptions()
                        .position(randomLatLng)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.star_icon40))

                    Map.addMarker(markerOptions)
                }
            } else {
                Toast.makeText(this, "Posizione non trovata", Toast.LENGTH_SHORT).show()
            }
        }

            /*
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

       */
            Map.setOnMarkerClickListener(this)
    }


    // Funzione per calcolare le coordinate casuali
    private fun calculateLatLng(user: LatLng, distance: Double, angle: Double): LatLng {
        val radiusEarth = 6371e3 // Raggio della Terra in metri
        val lat1 = Math.toRadians(user.latitude)
        val lng1 = Math.toRadians(user.longitude)
        val angularDistance = distance / radiusEarth

        val lat2 = Math.asin(Math.sin(lat1) * Math.cos(angularDistance) +
                Math.cos(lat1) * Math.sin(angularDistance) * Math.cos(angle))
        val lng2 = lng1 + Math.atan2(Math.sin(angle) * Math.sin(angularDistance) * Math.cos(lat1),
            Math.cos(angularDistance) - Math.sin(lat1) * Math.sin(lat2))

        return LatLng(Math.toDegrees(lat2), Math.toDegrees(lng2))
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

                    Map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f))
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
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

    //markerClick
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

                    //val ptStar = marker.tag as? Int //acquisisce il valore del tag
                    //if (ptStar != null) {
                        //ptTot += ptStar //somma il valore del tag al punteggio
                        ptTot += 10 //in caso di stelline casuali

                        marker.isVisible = false // nasconde la stellina

                        //Incremento progress bar
                        currentProgress += 10
                        ProgressBar.progress = currentProgress
                        ProgressBar.max = 100 //valore massimo della progress bar


                        val longMessCongrat = "Hai catturato una stella! \nIl tuo punteggio è: $ptTot"
                        Toast.makeText(this, longMessCongrat, Toast.LENGTH_SHORT).show()
                    /*} else {
                        Toast.makeText(this, "Ops, cattura non riuscita. Riprova!", Toast.LENGTH_SHORT).show()
                    }*/
                } else {
                    val missingDistance = distance.toInt()
                    val longMessDistance = "Ti mancano $missingDistance metri alla stellina!"
                    Toast.makeText(this, longMessDistance, Toast.LENGTH_SHORT).show()
                }
           }

        //al raggiungimento dei 100 punti chiama il metodo per terminare il livello
        if(ptTot == 100) {
            levelEnded()
        }

        return true
    }

    //metodo fine livello
    fun levelEnded(){

        Time.stop() //ferma il cronometro

        // Conversione dei minuti in secondi
        val timeInSecond = timeConverter(Time)

        // Calcolo finale punti
        val finalPt = totalPoint(timeInSecond)

        //salvataggio dei punti nel database
        val point = finalPt
        db.savePoint(point)

        //apertura schermata di fine livello
        val intent = Intent(this@GameActivity, LevelEnded::class.java)
        intent.putExtra("Punteggio finale:", finalPt)
        startActivity(intent)
    }

    //metodo conversione minuti in secondi
    private fun timeConverter(Time: Chronometer): Int{

        val timeInMillis = SystemClock.elapsedRealtime() - Time.base
        val timeInSecond = timeInMillis/1000
        val timeInMinute = timeInSecond.toDouble()

        val min = (timeInMinute / 60).toInt()
        val sec = (timeInMinute % 60).toInt()
        val formatTime = String.format("%02d:%02d", min, sec)

        //salvataggio del tempo nel database
        db.saveTime(formatTime)

        return timeInSecond.toInt()
    }

    //metodo gestione cronometro e calcolo punti
    fun totalPoint(timeInSecond: Int): Int {

        var finalPt = ptTot

        if(timeInSecond > 600) {
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
