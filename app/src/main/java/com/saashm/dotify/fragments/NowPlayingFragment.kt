package com.saashm.dotify.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.saashm.dotify.DotifyApp
import com.saashm.dotify.R
import com.saashm.dotify.backend.Song
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

class NowPlayingFragment: Fragment() {
    private lateinit var currSong: Song
    private var num by Delegates.notNull<Int>()

    companion object {
        val TAG: String = NowPlayingFragment::class.java.simpleName
        fun getInstance(): NowPlayingFragment {
           return NowPlayingFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = context?.applicationContext as DotifyApp
        app.songManager.currentSong?.let {
           currSong = it
       }
        app.songManager.count.let{
            num = it
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

    fun updateSong(song: Song?) {
        if(song != null) {
            tvSongTitle.text = song.title
            tvArtistInfo.text = song.artist
            Picasso.get().load(song.largeImageURL).into(ibAlbumCover)
            currSong = song
        }
    }
    // Private functions
    private fun iteratePlays() {
        num += 1
        val playContent = "$num plays"
        tvNumPlays.text = playContent
        (context?.applicationContext as DotifyApp).songManager.iterateCount()
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