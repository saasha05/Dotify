package com.saashm.dotify.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.saashm.dotify.R
import com.saashm.dotify.SongAdapter
import com.saashm.dotify.fragments.NowPlayingFragment
import com.saashm.dotify.fragments.NowPlayingFragment.Companion.ARG_SONG
import com.saashm.dotify.fragments.SongListFragment
import kotlinx.android.synthetic.main.activity_fragment_container.*

class FragmentContainerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_container)
        val songList = SongDataProvider.getAllSongs()
        val songListFragment = SongListFragment()
        val nowPlayingFragment = NowPlayingFragment()

        val argumentBundle = Bundle().apply {
            val list = // song list into arrayList
            putParcelable(ARG_SONG, list)
        }
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragContainer, songListFragment)
            .commit()

        var clickedSong:Song? = null
        val allSongs: List<Song> = SongDataProvider.getAllSongs()
        val songAdapter = SongAdapter(allSongs)

        // Not working rn??
        songAdapter.onSongClickListener = {song ->
            tvCurrSong.text = getString(R.string.song_artist, song.title, song.artist)
            clickedSong = song
            Unit
        }

        miniPlayer.setOnClickListener {
            miniPlayer.visibility = View.INVISIBLE
            // Add Now playing fragment with selected song
            val argumentBundle = Bundle().apply {
                putParcelable(ARG_SONG, clickedSong)
            }
            nowPlayingFragment.arguments = argumentBundle
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, nowPlayingFragment)
                .commit()
            // If it already exists then update song?

        }
        btnShuffle.setOnClickListener {
            songListFragment.shuffleList()
        }
    }

}
