package com.saashm.dotify.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.saashm.dotify.OnSongClickListener
import com.saashm.dotify.R
import com.saashm.dotify.SongAdapter
import com.saashm.dotify.fragments.NowPlayingFragment
import com.saashm.dotify.fragments.NowPlayingFragment.Companion.ARG_SONG
import com.saashm.dotify.fragments.SongListFragment
import kotlinx.android.synthetic.main.activity_fragment_container.*

class FragmentContainerActivity : AppCompatActivity(), OnSongClickListener {
    private var clickedSong: Song? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_container)
        val songList = SongDataProvider.getAllSongs()
        val songListFragment = SongListFragment()
        val allSongsBundle = Bundle().apply {
            val list = ArrayList(songList)
            putParcelableArrayList(ARG_SONG, list)
        }
        songListFragment.arguments = allSongsBundle
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragContainer, songListFragment)
            .commit()

        miniPlayer.setOnClickListener {
            if(clickedSong != null) {
                miniPlayer.visibility = View.INVISIBLE
                showNowPlaying(clickedSong)
            }
        }
        btnShuffle.setOnClickListener {
            songListFragment.shuffleList()
        }
    }
    private fun showNowPlaying(song: Song?) {
        // Add Now playing fragment with selected song
        val nowPlayingFragment = NowPlayingFragment()
        val argumentBundle = Bundle().apply {
            putParcelable(ARG_SONG, song)
        }
        nowPlayingFragment.arguments = argumentBundle
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragContainer, nowPlayingFragment)
            .commit()
        // If it already exists then update song?

    }

    override fun onSongClicked(song: Song) {
        tvCurrSong.text = getString(R.string.song_artist, song.title, song.artist)
        clickedSong = song
    }

}
