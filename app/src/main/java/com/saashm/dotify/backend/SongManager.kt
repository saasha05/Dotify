package com.saashm.dotify.backend

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ericchee.songdataprovider.Song
import com.google.gson.Gson

class SongManager(context: Context) {
    private val queue: RequestQueue = Volley.newRequestQueue(context)
    var currentSong: Song? = null
    lateinit var allSongs: List<Song>
    var onSongClickListener: OnSongClickListener? = null
    init {
        getAllSongs({
            allSongs = it.songs
        })
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
    fun getAllSongs(onListReady: (AllSongs) -> Unit, onError: (() -> Unit)? = null) {
        val emailURL = "https://raw.githubusercontent.com/echeeUW/codesnippets/master/emails.json"
        val request = StringRequest(
            Request.Method.GET, emailURL,
            { response ->
                // Success
                val gson = Gson()
                val allEmails = gson.fromJson(response, AllSongs::class.java )
                onListReady(allEmails)
            },
            {
                onError?.invoke()
            }
        )
        queue.add(request)
    }

}