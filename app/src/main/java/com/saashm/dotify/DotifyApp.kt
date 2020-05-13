package com.saashm.dotify

import android.app.Application
import android.util.Log
import android.widget.Toast
import com.saashm.dotify.backend.Song
import com.saashm.dotify.backend.SongManager
import com.saashm.dotify.fragments.SongListFragment
import com.squareup.picasso.Picasso

class DotifyApp: Application() {
    lateinit var songManager: SongManager
    override fun onCreate() {
        super.onCreate()
        songManager = SongManager(this)
        songManager.toggleSpinner()
        songManager.getAllSongs({list ->
            songManager.allSongs = list.songs
            songManager.updateList()
        }, {
            Toast.makeText(this, "Could not fetch song list", Toast.LENGTH_LONG).show()
        })
    }
}