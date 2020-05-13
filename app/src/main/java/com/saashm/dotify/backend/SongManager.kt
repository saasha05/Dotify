package com.saashm.dotify.backend

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlin.random.Random

class SongManager(context: Context) {
    private val queue: RequestQueue = Volley.newRequestQueue(context)
    var currentSong: Song? = null
    var allSongs: List<Song>? = null
    var onSongClickListener: OnSongClickListener? = null
    var listUpdate: UpdateListListener? = null
    var count: Int
    init {
        count = Random.nextInt(1000, 99999)
    }

    fun onSongClicked(song: Song) {
        currentSong = song
        count = Random.nextInt(1000, 99999)
        onSongClickListener?.onSongClicked(song)
    }
    fun shuffle() {
        allSongs = allSongs!!.toMutableList().apply {
            shuffle()
        }.toList()
    }
    fun updateList() {
        listUpdate?.onListUpdate()
    }
    fun iterateCount() {
        count++
    }
    fun toggleSpinner() {
        listUpdate?.toggleSpinner()
    }
    fun getAllSongs(onListReady: (AllSongs) -> Unit, onError: (() -> Unit)? = null) {
        val emailURL = "https://raw.githubusercontent.com/echeeUW/codesnippets/master/musiclibrary.json"
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
interface UpdateListListener {
    fun onListUpdate()
    fun toggleSpinner()
}