package it.uninsubria.catchstars

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import it.uninsubria.catchstars.data.model.Astronauta
import it.uninsubria.catchstars.database.DataBaseHelper
import java.sql.Time


class ScoreActivity : AppCompatActivity() {

    private lateinit var TableScore: TableLayout
    private lateinit var LevelRow: TableRow
    private lateinit var DataLevel: TextView
    private lateinit var PointLevel: TextView
    private lateinit var TimeLevel: TextView
    private lateinit var BackButton: Button

    lateinit var database: DataBaseHelper
    lateinit var db : SQLiteDatabase

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //apre tabella come popup
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = layoutParams
        setContentView(R.layout.score)

        TableScore = findViewById(R.id.scoreTable)
        LevelRow = findViewById(R.id.row)
        DataLevel = findViewById(R.id.data)
        PointLevel = findViewById(R.id.points)
        TimeLevel = findViewById(R.id.time)
        BackButton = findViewById(R.id.back_button)

        //collegamento con il databaseHelper
        database = DataBaseHelper(this)
        db = database.writableDatabase

        //todo popolamento tabella: data - pt - time
        //recupera dati da database e li stampa nella tabella
        val cursor = db.rawQuery("SELECT * FROM Points", null)

        Toast.makeText(this, "Pre-if", Toast.LENGTH_SHORT).show() //todo messaggi di controllo - da eliminare

        if (cursor.moveToFirst()) {
            Toast.makeText(this, "If-Pre-do", Toast.LENGTH_SHORT).show() //todo messaggi di controllo - da eliminare
            do {
                val columnDate = cursor.getColumnIndex("DateLevel")
                val columnPoint = cursor.getColumnIndex("PointLevel")
                val columnTime = cursor.getColumnIndex("TimeLevel")

                //controlla la validità degli indici
                if (columnDate != -1 && columnPoint != -1 && columnTime != -1) {

                    Toast.makeText(this, "Indici validi", Toast.LENGTH_SHORT).show() //todo messaggi di controllo - da eliminare

                    val date = cursor.getString(columnDate)
                    val points = cursor.getInt(columnPoint)
                    val time = cursor.getString(columnTime)

                    LevelRow = TableRow(this)
                    val values = TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                    )
                    LevelRow.layoutParams = values

                    //padding textview
                    values.setMargins(3,3,3,3)

                    //inserimento dei valori nelle celle della tabella
                    DataLevel = TextView(this)
                    DataLevel.text = date
                    LevelRow.addView(DataLevel)

                    PointLevel = TextView(this)
                    PointLevel.text = points.toString()
                    LevelRow.addView(PointLevel)

                    TimeLevel = TextView(this)
                    TimeLevel.text = time
                    LevelRow.addView(TimeLevel)

                    TableScore.addView(LevelRow)

                } else {
                    Toast.makeText(this, "Errore nella lettura dei dati", Toast.LENGTH_SHORT).show()
                }

            } while (cursor.moveToNext())

            cursor.close()
            db.close()

        } else {
            Toast.makeText(this, "Non sono presenti dati", Toast.LENGTH_SHORT).show()
        }

        //backButton
        BackButton.setOnClickListener{
            val intent = Intent(this@ScoreActivity, HomeGameActivity::class.java)
            startActivity(intent)
        }
    }

}