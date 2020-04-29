package com.saashm.dotify.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.saashm.dotify.OnSongClickListener
import com.saashm.dotify.R
import com.saashm.dotify.SongAdapter
import com.saashm.dotify.fragments.NowPlayingFragment.Companion.ARG_SONG
import kotlinx.android.synthetic.main.activity_fragment_container.*
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListFragment: Fragment() {
    private lateinit var songAdapter: SongAdapter
    private var onSongClickListener: OnSongClickListener? = null
    private lateinit var songList: List<Song>
    companion object {
        val TAG: String = SongListFragment::class.java.simpleName
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // get list of songs
        arguments?.let { args ->
            this.songList = args.getParcelableArrayList<Song>(ARG_SONG)!!.toList()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnSongClickListener) {
            onSongClickListener = context
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.activity_song_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var allSongs: List<Song> = SongDataProvider.getAllSongs()
        songAdapter = SongAdapter(allSongs)
        rvSongs.adapter = songAdapter
        // to change mini player text
        songAdapter.onSongClickListener = { song ->
            onSongClickListener?.onSongClicked(song)
        }
    }

    fun shuffleList() {
        val newSongs = songList.shuffled()
        songAdapter.change(newSongs)
    }
}