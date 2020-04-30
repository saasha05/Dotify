package com.saashm.dotify.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.saashm.dotify.OnSongClickListener
import com.saashm.dotify.R
import com.saashm.dotify.fragments.NowPlayingFragment
import com.saashm.dotify.fragments.NowPlayingFragment.Companion.ARG_SONG
import com.saashm.dotify.fragments.NowPlayingFragment.Companion.TAG
import com.saashm.dotify.fragments.SongListFragment
import kotlinx.android.synthetic.main.activity_fragment_container.*
import kotlinx.android.synthetic.main.activity_song_list.*

class FragmentContainerActivity : AppCompatActivity(), OnSongClickListener {
    private var clickedSong: Song? = null
    private val ARG_CURR_SONG: String = "arg_curr_song"
    private val ARG_SONG_LIST = "arg_song_list"
    private lateinit var songList: List<Song>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_container)
        val songListFragment = SongListFragment()
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                clickedSong = getParcelable(ARG_CURR_SONG)
                clickedSong?.let {
                    onSongClicked(it)
                }
                var newList = getParcelableArrayList<Song>(ARG_SONG_LIST)
                newList?.let {
                    songList = newList.toList()
                    // give it to songlist?
                    retainList()
                }
            }
        } else {
            songList = SongDataProvider.getAllSongs()
            showSongList(songListFragment)
        }
        checkBackStack()
        setOnClickListeners(songListFragment)
    }
    override fun onSongClicked(song: Song) {
        tvCurrSong.text = getString(R.string.song_artist, song.title, song.artist)
        clickedSong = song
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return super.onNavigateUp()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(ARG_CURR_SONG, clickedSong)
        outState.putParcelableArrayList(ARG_SONG_LIST, ArrayList(songList))
    }
    private fun getNowPlayingFragment() = supportFragmentManager.findFragmentByTag(TAG) as? NowPlayingFragment
    private fun showNowPlaying() {
        // Add Now playing fragment with selected song
        var nowPlayingFragment = getNowPlayingFragment()
        if(nowPlayingFragment == null) {
            nowPlayingFragment = NowPlayingFragment()
            val argumentBundle = Bundle().apply {
                putParcelable(ARG_SONG, clickedSong)
            }
            nowPlayingFragment.arguments = argumentBundle
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, nowPlayingFragment, TAG)
                .addToBackStack(TAG)
                .commit()
        } else {
            // If it already exists then update song
            nowPlayingFragment.updateSong(clickedSong)

        }

    }
    private fun showSongList(songListFragment: SongListFragment) {
        val allSongsBundle = getBundleSongList(songList)
        songListFragment.arguments = allSongsBundle
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragContainer, songListFragment, SongListFragment.TAG)
            .commit()
    }
    private fun retainList() {
        //  find the songlistfragment by tag, give it the data through
        //  some exposed fun like SongListFragment.updateList()
        //  songlistfragment updates adapter with list from activity
        val songListFrag = supportFragmentManager.findFragmentByTag(SongListFragment.TAG) as? SongListFragment
        if(songListFrag != null) {
            songListFrag.updateList(songList)
        }
    }
    private fun getBundleSongList(songList: List<Song>): Bundle {
        val allSongsBundle = Bundle().apply {
            val list = ArrayList(songList)
            putParcelableArrayList(ARG_SONG_LIST, list)
        }
        return (allSongsBundle)
    }
    private fun setOnClickListeners(songListFragment: SongListFragment) {
        miniPlayer.setOnClickListener {
            if(clickedSong != null) {
                miniPlayer.visibility = View.INVISIBLE
                showNowPlaying()
            }
        }
        btnShuffle.setOnClickListener {
            songListFragment.shuffleList()
            rvSongs.smoothScrollToPosition(0)
        }
        // to show back button based on stack
        supportFragmentManager.addOnBackStackChangedListener {
            val hasBackStack = supportFragmentManager.backStackEntryCount > 0
            if(hasBackStack) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                miniPlayer.visibility = View.VISIBLE
            }
        }
    }
    private fun checkBackStack() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            miniPlayer.visibility = View.INVISIBLE
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

}
