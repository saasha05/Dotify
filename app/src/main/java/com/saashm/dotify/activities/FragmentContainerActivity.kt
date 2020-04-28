package com.saashm.dotify.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ericchee.songdataprovider.SongDataProvider
import com.saashm.dotify.R
import com.saashm.dotify.fragments.SongListFragment

class FragmentContainerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_container)
        val songListFragment = SongListFragment()
        val songList = SongDataProvider.getAllSongs()


        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragContainer, songListFragment)
            .commit()
    }
}
