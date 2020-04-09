package com.saashm.dotify

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var num = Random.nextInt(1000, 99999)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val playContent = "$num plays"
        tvNumPlays.text = playContent

        ibAlbumCover.setOnLongClickListener{
           changeTextColor()
            true
        }
    }

    fun iteratePlays(view: View) {
        num += 1;
        val playContent = "$num plays"
        tvNumPlays.text = playContent
    }
    fun prevTrack(view: View) {
        skippingToast(view,"previous")
    }
    fun nextTrack(view: View) {
        skippingToast(view,"next")
    }
    private fun skippingToast(view: View, direction: String) {
        Toast.makeText(this, "Skipping to $direction track", Toast.LENGTH_SHORT).show()
    }
    fun changeUser(view: View) {
        llChangeUserContainer.visibility = View.INVISIBLE
        llApplyUserContainer.visibility = View.VISIBLE

    }

    fun applyUser(view: View) {
        val newUsername = etUsername.text.toString()
        if(newUsername.trim().isEmpty()) {
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show()
        } else {
            tvUsername.text = newUsername
            llChangeUserContainer.visibility = View.VISIBLE
            llApplyUserContainer.visibility = View.INVISIBLE
        }
    }
    private fun changeTvColor(color: Int) {
        tvUsername.setTextColor(ContextCompat.getColor(this, color))
        tvSongTitle.setTextColor(ContextCompat.getColor(this, color))
        tvArtistInfo.setTextColor(ContextCompat.getColor(this, color))
        tvNumPlays.setTextColor(ContextCompat.getColor(this, color))

    }
    private fun changeTextColor() {
        val colors = listOf(R.color.purple, R.color.darkBlue, R.color.green, R.color.neonBlue, R.color.orange, R.color.red)
        changeTvColor(colors[Random.nextInt(1, 6)])


    }

}
