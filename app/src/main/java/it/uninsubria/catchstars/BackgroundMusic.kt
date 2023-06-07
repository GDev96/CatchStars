package it.uninsubria.catchstars

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class BackgroundMusic : Service(){
    internal lateinit var player: MediaPlayer
    override fun onBind(arg0: Intent): IBinder? {

        return null
    }

    //todo https://stackoverflow.com/questions/58063609/how-to-play-background-music-through-all-activities-using-kotlin


}
