package com.saashm.dotify

import android.app.Application
import com.saashm.dotify.backend.SongManager

class DotifyApp: Application() {
    lateinit var songManager: SongManager
    override fun onCreate() {
        super.onCreate()
        songManager = SongManager()
    }

}