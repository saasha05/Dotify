package com.saashm.dotify.backend

import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider

class SongManager {
    var currentSong: Song? = null
    var allSongs: List<Song>
//    var songChangeListener: OnSongChangeListener? = null
    init {
        allSongs = SongDataProvider.getAllSongs()
    }
    fun updateCurrentSong(song: Song) {
        currentSong = song
//        songChangeListener?.onSongChange(song)
    }
    fun shuffle() {
        allSongs = allSongs.toMutableList().apply {
            shuffle()
        }.toList()
    }
}