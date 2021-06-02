package com.gilsoncoding.ad340_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.gilsoncoding.ad340_project.MainActivity.Companion.EXTRA_MESSAGE

class DisplayDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_details)

        // to display the clicked movie title text:
        val message = intent.extras?.getStringArray("details")

        //started working on a default image, but didn't complete that
        val pic = findViewById<ImageView>(R.id.mImage)

        val titleView = findViewById<TextView>(R.id.mtitle)
        val yearView = findViewById<TextView>(R.id.mYear)
        val dirView = findViewById<TextView>(R.id.mDirector)
        val descView = findViewById<TextView>(R.id.mDesc)

        titleView.text = message?.get(0)
        yearView.text = message?.get(1)
        dirView.text = "Dir: " + message?.get(2)
        Picasso.get().load(message?.get(3)).into(pic)
        descView.text = message?.get(4)

    }
}