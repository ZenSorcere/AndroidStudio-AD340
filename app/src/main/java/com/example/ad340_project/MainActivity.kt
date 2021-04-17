package com.example.ad340_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // Kotlin function for a Toast
    // gButton1 is the only one this is implemented on yet.
    // all other buttons do nothing onClick, but gButton1 makes a noise
    // But no Toast appears. :/
    fun showLuke(view: View) {
    val text = "Luke Skywalker"
    val duration = Toast.LENGTH_SHORT

    val luke = Toast.makeText(applicationContext, text, duration).show()
    }

}