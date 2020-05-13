package com.saashm.dotify.backend

import com.ericchee.songdataprovider.Song

data class AllSongs (
    val title: String,
    val numOfSongs: Int,
    val songs: List<Song>
)