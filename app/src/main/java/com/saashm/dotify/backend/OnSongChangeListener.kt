package com.saashm.dotify.backend

import com.ericchee.songdataprovider.Song

interface OnSongChangeListener {
    fun onSongChange(song: Song)
}