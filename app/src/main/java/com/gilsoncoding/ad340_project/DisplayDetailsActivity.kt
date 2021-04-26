package com.gilsoncoding.ad340_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DisplayDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_details)

        // to display the clicked movie title text:
        val intent = this.intent.extras
        val message = intent?.getStringArray("details")

        val titleView = findViewById<TextView>(R.id.mtitle)
        val yearView = findViewById<TextView>(R.id.mYear)
        val dirView = findViewById<TextView>(R.id.mDirector)
        val descView = findViewById<TextView>(R.id.mDesc)

        titleView.text = message?.get(0)
        yearView.text = message?.get(1)
        dirView.text = message?.get(2)
        descView.text = message?.get(4)

    }
}