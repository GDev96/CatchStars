package it.uninsubria.catchstars

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class BackgroundMusic : Service(){
    internal lateinit var musicplayer: MediaPlayer
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        musicplayer = MediaPlayer.create(this, R.raw.spiritual_moment_cut)
        musicplayer.setLooping(true); // Loop musica
    }

    override fun onDestroy() {
        super.onDestroy()
        musicplayer.stop()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        musicplayer.start()
        return super.onStartCommand(intent, flags, startId)
    }

}
