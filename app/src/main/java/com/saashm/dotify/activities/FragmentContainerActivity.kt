package com.saashm.dotify.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.ericchee.songdataprovider.Song
import com.saashm.dotify.DotifyApp
import com.saashm.dotify.backend.OnSongClickListener
import com.saashm.dotify.R
import com.saashm.dotify.backend.OnSongChangeListener
import com.saashm.dotify.backend.SongManager
import com.saashm.dotify.fragments.NowPlayingFragment
import com.saashm.dotify.fragments.NowPlayingFragment.Companion.TAG
import com.saashm.dotify.fragments.SongListFragment
import kotlinx.android.synthetic.main.activity_fragment_container.*

class FragmentContainerActivity : AppCompatActivity(),
    OnSongClickListener {
    private var clickedSong: Song? = null
    lateinit var manager: SongManager
//    private val ARG_CURR_SONG: String = "arg_curr_song"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_container)
        manager = (applicationContext as DotifyApp).songManager
//        manager.songChangeListener = this
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, SongListFragment.getInstance(), SongListFragment.TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
        }
        clickedSong = manager.currentSong
        clickedSong?.let {
            tvCurrSong.text = getString(R.string.song_artist, it.title, it.artist)
        }
//        else {
//            with(savedInstanceState) {
//                clickedSong = getParcelable(ARG_CURR_SONG)
//                clickedSong?.let {
//                    onSongClicked(it)
//                }
//            }
//        }
        checkBackStack()
        setOnClickListeners()
    }
    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return super.onNavigateUp()
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putParcelable(ARG_CURR_SONG, clickedSong)
//    }
    private fun getNowPlayingFragment() = supportFragmentManager.findFragmentByTag(TAG) as? NowPlayingFragment

    private fun showNowPlaying() {
        // Add Now playing fragment with selected song
        var nowPlayingFragment = getNowPlayingFragment()
        if(nowPlayingFragment == null) {
            nowPlayingFragment = NowPlayingFragment.getInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, nowPlayingFragment, TAG)
                .addToBackStack(TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        } else {
            // If it already exists then update song
            nowPlayingFragment.updateSong(clickedSong)
        }
    }
    private fun setOnClickListeners() {
        miniPlayer.setOnClickListener {
            if(clickedSong != null) {
                miniPlayer.visibility = View.INVISIBLE
                showNowPlaying()
            }
        }
        btnShuffle.setOnClickListener {
            val songListFragment = supportFragmentManager.findFragmentByTag(SongListFragment.TAG) as? SongListFragment
            songListFragment?.shuffleList()
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
    override fun onSongClicked(song: Song) {
        tvCurrSong.text = getString(R.string.song_artist, song.title, song.artist)
        manager.updateCurrentSong(song)
    }

//    override fun onSongChange(song: Song) {
//        Toast.makeText(this, "ON SONG CHANGE", Toast.LENGTH_SHORT)
//    }

}
