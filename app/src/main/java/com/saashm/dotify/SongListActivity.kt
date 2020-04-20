package com.saashm.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)
        val allSongs: List<Song> = SongDataProvider.getAllSongs()
        val songAdapter = SongAdapter(allSongs)
        rvSongs.adapter = songAdapter

        // Clicking on a song will change the name on the mini player
        songAdapter.onSongClickListener = {song ->
            tvCurrSong.text = getString(R.string.song_artist, song.title, song.artist)
            Unit
        }
        // Clicking shuffle button will shuffle order of list
        btnShuffle.setOnClickListener {
            val newSongs = allSongs.shuffled()
            songAdapter.change(newSongs)
        }
    }
}
