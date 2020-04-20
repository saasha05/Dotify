package com.saashm.dotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
        val currSong = listOfSongs[position]
        val songName = currSong.title
        val artistName = currSong.artist
        val songImage = currSong.smallImageID
        holder.bind(songName, artistName, songImage)

    }

    class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvSongName by lazy {itemView.findViewById<TextView>(R.id.tvSongName)}
        private val tvArtistName by lazy {itemView.findViewById<TextView>(R.id.tvArtistName)}
        private val ivSongImage by lazy {itemView.findViewById<ImageView>(R.id.ivSongImage)}
        fun bind(songName: String, artistName:String, songImage:Int) {
            tvSongName.text = songName
            tvArtistName.text = artistName
            ivSongImage.setImageResource(songImage)
            ivSongImage.contentDescription = songName + "-" + artistName + "cover image"
        }
    }

}