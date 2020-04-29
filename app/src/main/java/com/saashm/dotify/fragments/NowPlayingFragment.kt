package com.saashm.dotify.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import com.saashm.dotify.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates
import kotlin.random.Random

class NowPlayingFragment: Fragment() {
    private lateinit var currSong: Song
    private var num by Delegates.notNull<Int>()

    companion object {
        val TAG: String = NowPlayingFragment::class.java.simpleName
        const val ARG_SONG = "arg_song"
        const val ARG_COUNT = "arg_count"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                val song: Song? = getParcelable(ARG_SONG)
                val count = getInt(ARG_COUNT)
                song?.let {
                    currSong = it
                }
                count.let {
                    num = it
                }
            }
        } else {
            // default behavior
            arguments?.let { args ->
                val song = args.getParcelable<Song>(ARG_SONG)
                if (song != null) {
                    this.currSong = song
                }
                val count = args.getInt(ARG_COUNT)
                count.let {
                    this.num = it
                }

            }
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
        outState.putParcelable(ARG_SONG, currSong)
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
    private fun changeUser() {
        llChangeUserContainer.visibility = View.GONE
        llApplyUserContainer.visibility = View.VISIBLE

    }
    private fun applyUser() {
        val newUsername = etUsername.text.toString()
        if(newUsername.trim().isEmpty()) {
            Toast.makeText(context, "Please enter a username", Toast.LENGTH_SHORT).show()
        } else {
            tvUsername.text = newUsername
            llChangeUserContainer.visibility = View.VISIBLE
            llApplyUserContainer.visibility = View.INVISIBLE
        }
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
        btnChangeUser.setOnClickListener {
            changeUser()
        }
        btnApplyUser.setOnClickListener {
            applyUser()
        }
    }
}