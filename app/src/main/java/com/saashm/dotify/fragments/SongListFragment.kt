package com.saashm.dotify.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
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
        const val ARG_SONG_LIST = "arg_song_list"
        val TAG: String = SongListFragment::class.java.simpleName
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
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                songList = getParcelableArrayList<Song>(ARG_SONG_LIST) as List<Song>
            }
        }
        arguments?.let { args ->
            songList = args.getParcelableArrayList<Song>(ARG_SONG_LIST) as List<Song>
        }
        return layoutInflater.inflate(R.layout.activity_song_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        songAdapter = SongAdapter(songList)
        rvSongs.adapter = songAdapter
        // to change mini player text when song is clicked
        songAdapter.onSongClickListener = { song ->
            onSongClickListener?.onSongClicked(song)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(ARG_SONG_LIST, ArrayList(songList))
        super.onSaveInstanceState(outState)
    }

    fun shuffleList(): List<Song> {
        val newSongs = songList.shuffled()
        songAdapter.change(newSongs)
        songList = newSongs
        return songList
    }
}