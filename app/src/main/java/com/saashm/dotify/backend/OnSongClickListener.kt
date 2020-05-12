package com.saashm.dotify.backend

import com.ericchee.songdataprovider.Song

interface OnSongClickListener {
    fun onSongClicked(song: Song)

}