package com.saashm.dotify.backend

import com.ericchee.songdataprovider.Song

data class AllSongs (
    val title: String,
    val numOfSongs: Int,
    val songs: List<Song>
)

data class User (
    val username: String,
    val firstName: String,
    val lastName: String,
    val hasNose: Boolean?,
    val platform: Number?,
    val profilePicURL: String?
)