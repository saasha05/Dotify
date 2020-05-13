package com.saashm.dotify.backend

import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider

class SongManager {
    var currentSong: Song? = null
    var allSongs: List<Song>
    var onSongClickListener: OnSongClickListener? = null
    init {
        allSongs = SongDataProvider.getAllSongs()
    }
    fun onSongClicked(song: Song) {
        currentSong = song
        onSongClickListener?.onSongClicked(song)
    }
    fun shuffle() {
        allSongs = allSongs.toMutableList().apply {
            shuffle()
        }.toList()
    }
}