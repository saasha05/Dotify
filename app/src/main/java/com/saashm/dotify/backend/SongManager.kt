package com.saashm.dotify.backend

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.google.gson.Gson

class SongManager(context: Context) {
    private val queue: RequestQueue = Volley.newRequestQueue(context)
    var currentSong: Song? = null
    var allSongs: List<Song>? = null
    var onSongClickListener: OnSongClickListener? = null
    var listUpdate: UpdateListListener? = null

    fun onSongClicked(song: Song) {
        currentSong = song
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
                Log.i("test", "ERROR")
                onError?.invoke()
            }
        )
        queue.add(request)
    }

}
interface UpdateListListener {
    fun onListUpdate()
}