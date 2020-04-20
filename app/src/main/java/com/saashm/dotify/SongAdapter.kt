package com.saashm.dotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song

class SongAdapter(private val listOfSongs: List<Song>): RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongViewHolder(view)
    }

    override fun getItemCount() = listOfSongs.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val currSong = listOfSongs[position];
        val songName = currSong.title;
        holder.bind(songName);

    }

    class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvSongName by lazy {itemView.findViewById<TextView>(R.id.tvSongName)}
        fun bind(name: String) {
            tvSongName.text = name;
        }
    }

}