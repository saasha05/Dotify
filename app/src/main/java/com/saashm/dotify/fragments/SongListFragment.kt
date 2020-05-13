package com.saashm.dotify.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import com.saashm.dotify.DotifyApp
import com.saashm.dotify.backend.OnSongClickListener
import com.saashm.dotify.R
import com.saashm.dotify.backend.SongAdapter
import com.saashm.dotify.backend.SongManager
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListFragment: Fragment() {
    private lateinit var songAdapter: SongAdapter
    private var onSongClickListener: OnSongClickListener? = null
    private lateinit var songList: List<Song>
    private lateinit var manager: SongManager
    companion object {
        const val ARG_SONG_LIST = "arg_song_list"
        val TAG: String = SongListFragment::class.java.simpleName
        fun getInstance() : SongListFragment {
            return SongListFragment()
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSongClickListener) {
            onSongClickListener = context
        }
        manager = (context.applicationContext as DotifyApp).songManager
        songList = manager.allSongs
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        if (savedInstanceState != null) {
//            with(savedInstanceState) {
//                songList = getParcelableArrayList<Song>(ARG_SONG_LIST) as List<Song>
//            }
//        } else {
//            arguments?.let { args ->
//                songList = args.getParcelableArrayList<Song>(ARG_SONG_LIST) as List<Song>
//            }
//        }
//
//    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.activity_song_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        songAdapter = SongAdapter(songList)
        rvSongs.adapter = songAdapter
        // to change mini player text when song is clicked
        songAdapter.onSongClickListener = { song ->
            (context?.applicationContext as DotifyApp).songManager.onSongClicked(song)
            onSongClickListener?.onSongClicked(song)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(ARG_SONG_LIST, ArrayList(songList))
    }

    fun shuffleList() {
        manager.shuffle()
        songAdapter.change(manager.allSongs)
        rvSongs.smoothScrollToPosition(0)
    }
}