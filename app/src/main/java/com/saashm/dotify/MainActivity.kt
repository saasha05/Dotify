package com.saashm.dotify

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
    private fun changeTextColor() {
        when(Random.nextInt(1, 4)) {
            1 -> { theme.applyStyle(R.style.style1, true)
                setContentView(R.layout.activity_main) }
            2 -> { theme.applyStyle(R.style.style2, true)
                setContentView(R.layout.activity_main) }
            3 -> { theme.applyStyle(R.style.style3, true)
                setContentView(R.layout.activity_main) }
        }
    }

}
