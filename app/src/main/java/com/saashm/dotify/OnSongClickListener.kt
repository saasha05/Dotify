package com.saashm.dotify

import com.ericchee.songdataprovider.Song

interface OnSongClickListener {
    companion object {
        fun onSongClicked(song: Song) {}
    }

}