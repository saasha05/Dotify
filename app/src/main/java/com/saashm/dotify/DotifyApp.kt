package com.saashm.dotify

import android.app.Application
import android.util.Log
import android.widget.Toast
import com.saashm.dotify.backend.SongManager

class DotifyApp: Application() {
    lateinit var songManager: SongManager
    override fun onCreate() {
        super.onCreate()
        songManager = SongManager(this)
        songManager.getAllSongs({list ->
            Log.i("saashm", "$list.songs")
            songManager.allSongs = list.songs
        })

    }

}