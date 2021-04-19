package com.gilsoncoding.ad340_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

//    val btnArray = arrayOf("Luke", "Leia", "Han", "Chewie", "R2-D2", "C-3P0")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val myButton: Button = findViewById(R.id.gButton1)
//        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, btnArray)
//        val gridView: GridView = findViewById(R.id.grid1)
//        gridView.adapter = adapter
//

    }



    // Kotlin function for a Toast
    // pulls button's text and creates a Toast for that button
    fun showText(view: View) {
        //val button: Button = findViewById(R.id.gButton1)
        val button = view as Button
        val context = applicationContext
        val duration = Toast.LENGTH_SHORT
        val text = button.text

        val btntext = Toast.makeText(context, text, duration).show()
    }




}