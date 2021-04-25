package com.gilsoncoding.ad340_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DisplayDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_details)

        // to display the clicked movie title text:
        val intent = intent
        val message = intent.getStringExtra(DisplayMoviesActivity.EXTRA_MESSAGE)
        val textView = findViewById<TextView>(R.id.textView7)
        textView.text = message
    }
}