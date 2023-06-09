package it.uninsubria.catchstars

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class InfoActivity (intent : Intent) : AppCompatActivity(){

    private lateinit var BackButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info_activity)

        BackButton = findViewById(R.id.back_button)

        BackButton.setOnClickListener{
            val intent = Intent(this@InfoActivity, SettingActivity::class.java)
            startActivity(intent)
        }

    }

}