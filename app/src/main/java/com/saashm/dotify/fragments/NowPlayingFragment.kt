package com.saashm.dotify.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import com.saashm.dotify.DotifyApp
import com.saashm.dotify.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates
import kotlin.random.Random

class NowPlayingFragment: Fragment() {
    private lateinit var currSong: Song
    private var num by Delegates.notNull<Int>()

    companion object {
        val TAG: String = NowPlayingFragment::class.java.simpleName
//        const val ARG_SONG = "arg_song"
        const val ARG_COUNT = "arg_count"
        fun getInstance(): NowPlayingFragment {
            //            nowPlayingFragment.arguments = Bundle().apply {
//                putParcelable(ARG_SONG, song)
//               count?.let{
//                   putInt(ARG_COUNT, count)
//               }
//            }
           return NowPlayingFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                val count = getInt(ARG_COUNT)
                count.let {
                    num = it
                }
            }
        } else {
            // default behavior
                num = Random.nextInt(1000, 99999)
        }
        val app = context?.applicationContext as DotifyApp
        app.songManager.currentSong?.let {
           currSong = it
       }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val playContent = "$num plays"
        tvNumPlays.text = playContent
        updateSong(currSong)
        setOnClickListeners()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(ARG_COUNT, num)
        super.onSaveInstanceState(outState)

    }
    fun updateSong(song: Song?) {
        if(song != null) {
            tvSongTitle.text = song.title
            tvArtistInfo.text = song.artist
            ibAlbumCover.setImageResource(song.largeImageID)
            currSong = song
        }
    }
    // Private functions
    private fun iteratePlays() {
        num += 1
        val playContent = "$num plays"
        tvNumPlays.text = playContent
    }
    private fun prevTrack() {
        skippingToast("previous")
    }
    private fun nextTrack() {
        skippingToast("next")
    }
    private fun skippingToast(direction: String) {
        Toast.makeText(context, "Skipping to $direction track", Toast.LENGTH_SHORT).show()
    }

    private fun setOnClickListeners() {
        btnPlay.setOnClickListener {
            iteratePlays()
        }
        btnNext.setOnClickListener {
            nextTrack()
        }
        btnPrev.setOnClickListener {
            prevTrack()
        }
    }
}