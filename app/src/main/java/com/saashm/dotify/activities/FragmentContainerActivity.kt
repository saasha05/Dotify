package com.saashm.dotify.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.saashm.dotify.OnSongClickListener
import com.saashm.dotify.R
import com.saashm.dotify.fragments.NowPlayingFragment
import com.saashm.dotify.fragments.NowPlayingFragment.Companion.ARG_COUNT
import com.saashm.dotify.fragments.NowPlayingFragment.Companion.ARG_SONG
import com.saashm.dotify.fragments.NowPlayingFragment.Companion.TAG
import com.saashm.dotify.fragments.SongListFragment
import kotlinx.android.synthetic.main.activity_fragment_container.*
import kotlin.random.Random

class FragmentContainerActivity : AppCompatActivity(), OnSongClickListener {
    private var clickedSong: Song? = null
    private val ARG_CURR_SONG: String = "arg_curr_song"
    private val ARG_IF_NOWPL: String = "arg_if_now_playing_present"
    private lateinit var songList: List<Song>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_container)
        // song List fragment always there
        songList = SongDataProvider.getAllSongs()
        val songListFragment = SongListFragment()
        val allSongsBundle = getBundleSongList(songList)
        songListFragment.arguments = allSongsBundle
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragContainer, songListFragment, SongListFragment.TAG)
            .commit()
        // Add logic to get old songlist
        val nowPl = getNowPlayingFragment()
        if(nowPl != null) {
            // Show the now playing fragment
            // OR add it to the top of stack
            Log.i("saashm", "HERE")
        } else {
            Log.i("saashm", "NOT HERE")
        }
        // If rotated or something get the old mini player stuff
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                clickedSong = getParcelable(ARG_CURR_SONG)
                clickedSong?.let {
                    onSongClicked(it)
                }
            }
        }
        // on click listeners
        miniPlayer.setOnClickListener {
            if(clickedSong != null) {
                miniPlayer.visibility = View.INVISIBLE
                showNowPlaying()
            }
        }
        btnShuffle.setOnClickListener {
            songListFragment.shuffleList()
        }
        supportFragmentManager.addOnBackStackChangedListener {
            val hasBackStack = supportFragmentManager.backStackEntryCount > 0
            if(hasBackStack) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }
    }
    override fun onSongClicked(song: Song) {
        tvCurrSong.text = getString(R.string.song_artist, song.title, song.artist)
        clickedSong = song
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(ARG_CURR_SONG, clickedSong)
        outState.putBoolean(ARG_IF_NOWPL, getNowPlayingFragment() != null)
    }
    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        // lazy way of getting mini player back
        miniPlayer.visibility = View.VISIBLE
        return super.onNavigateUp()
    }

    override fun onBackPressed() {
        // lazy way of getting mini player back
        miniPlayer.visibility = View.VISIBLE
        super.onBackPressed()
    }
    private fun getNowPlayingFragment() = supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) as? NowPlayingFragment
    private fun showNowPlaying() {
        // Add Now playing fragment with selected song
        var nowPlayingFragment = getNowPlayingFragment()
        if(nowPlayingFragment == null) {
            nowPlayingFragment = NowPlayingFragment()
            val argumentBundle = Bundle().apply {
                putParcelable(ARG_SONG, clickedSong)
                putInt(ARG_COUNT, Random.nextInt(1000, 99999))
            }
            nowPlayingFragment.arguments = argumentBundle
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, nowPlayingFragment, NowPlayingFragment.TAG)
                .addToBackStack(TAG)
                .commit()
        } else {
            // If it already exists then update song
            nowPlayingFragment.updateSong(clickedSong)

        }

    }
    private fun getBundleSongList(songList: List<Song>): Bundle {
        val allSongsBundle = Bundle().apply {
            val list = ArrayList(songList)
            putParcelableArrayList(ARG_SONG, list)
        }
        return (allSongsBundle)
    }



}
