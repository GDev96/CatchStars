package it.uninsubria.catchstars

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import it.uninsubria.catchstars.data.model.Astronauta
import it.uninsubria.catchstars.database.DataBaseHelper
import java.sql.Time


class ScoreActivity : AppCompatActivity() {

    private lateinit var ScrollView: ScrollView
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

        ScrollView = findViewById(R.id.scrollable)
        TableScore = findViewById(R.id.scoreTable)
        LevelRow = findViewById(R.id.row)
        DataLevel = findViewById(R.id.data)
        PointLevel = findViewById(R.id.points)
        TimeLevel = findViewById(R.id.time)
        BackButton = findViewById(R.id.back_button)

        //collegamento con il databaseHelper
        database = DataBaseHelper(this)
        db = database.writableDatabase

        //recupera dati da database e li stampa nella tabella
        val cursor = db.rawQuery("SELECT * FROM Points", null)

        if (cursor.moveToFirst()) {
            do {
                val columnDate = cursor.getColumnIndex("DateLevel")
                val columnPoint = cursor.getColumnIndex("PointLevel")
                val columnTime = cursor.getColumnIndex("TimeLevel")

                //controlla la validità degli indici
                if (columnDate != -1 && columnPoint != -1 && columnTime != -1) {

                    val date = cursor.getString(columnDate)
                    val points = cursor.getInt(columnPoint)
                    val time = cursor.getString(columnTime)

                    val font = ResourcesCompat.getFont(this, R.font.aclonica_regular)

                    LevelRow = TableRow(this)
                    val values = TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                    )
                    LevelRow.layoutParams = values

                    //inserimento dei valori nelle celle della tabella con stampa personalizzata
                    DataLevel = TextView(this)
                    DataLevel.text = date
                    DataLevel.gravity = Gravity.CENTER
                    DataLevel.typeface = font
                    DataLevel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                    DataLevel.setTextColor(Color.parseColor("#D7FCEF80"))
                    DataLevel.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)

                    PointLevel = TextView(this)
                    PointLevel.text = points.toString()
                    PointLevel.gravity = Gravity.CENTER
                    PointLevel.typeface = font
                    PointLevel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                    PointLevel.setTextColor(Color.parseColor("#D7FCEF80"))
                    PointLevel.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)

                    TimeLevel = TextView(this)
                    TimeLevel.text = time
                    TimeLevel.gravity = Gravity.CENTER
                    TimeLevel.typeface = font
                    TimeLevel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                    TimeLevel.setTextColor(Color.parseColor("#D7FCEF80"))
                    TimeLevel.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)

                    LevelRow.addView(DataLevel)
                    LevelRow.addView(PointLevel)
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