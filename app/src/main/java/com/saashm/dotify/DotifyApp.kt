package com.saashm.dotify

import android.app.Application
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider

class DotifyApp: Application() {
    //Application wide things to keep track of
    var currentSong: Song? = null
    lateinit var allSongs: List<Song>
    override fun onCreate() {
        super.onCreate()
        allSongs = SongDataProvider.getAllSongs()
    }
    fun updateCurrentSong(song: Song) {
        currentSong = song
    }
    fun updateSongs(songs: List<Song>) {
        allSongs = songs
    }

}