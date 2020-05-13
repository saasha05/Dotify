package com.saashm.dotify.backend


data class AllSongs (
    val title: String,
    val numOfSongs: Int,
    val songs: List<Song>
)
data class Song (
    val id: String,
    val title :String,
    val artist: String,
    val durationMillis: Int,
    val smallImageURL: String,
    val largeImageURL: String
)
