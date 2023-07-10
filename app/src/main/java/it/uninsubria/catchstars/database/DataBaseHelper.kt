package it.uninsubria.catchstars.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper (var context: Context) :  SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        val DATABASE_VERSION = 4
        val DATABASE_NAME = "AstroData"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE Points (" +
                "DateLevel DATE, " +
                "PointLevel INTEGER," +
                "TimeLevel TIME," +
                "PRIMARY KEY (DateLevel, PointLevel, TimeLevel)" +
                ")"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS Points")
        onCreate(db)
    }

    //salva i dati: data, punti e tempo dall'activity di gioco
    fun saveDate(date: String?){
        val database = writableDatabase

        val values = ContentValues()
        values.put("DateLevel", date)

        database.insert("Points", null, values)
        database.close()
    }

    fun savePoint(point: Int?){
        val database = writableDatabase

        val values = ContentValues()
        values.put("PointLevel", point)

        database.insert("Points", null, values)
        database.close()
    }

    fun saveTime(time: String?){
        val database = writableDatabase

        val values = ContentValues()
        values.put("TimeLevel", time)

        database.insert("Points", null, values)
        database.close()
    }

}


