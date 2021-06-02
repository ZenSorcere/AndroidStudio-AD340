package com.gilsoncoding.ad340_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager

class DisplayMoviesActivity : AppCompatActivity(), MoviesAdapter.OnItemClickLister  {

    lateinit var movies: Array<Array<String>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_movies)
        // Lookup the recyclerview in activity layout
        val recMovieList = findViewById<View>(R.id.recMovieList) as RecyclerView


        // Initialize movies
        movies = MovieModel().moviesL
        // Create adapter passing in the sample user data
        val adapter = MoviesAdapter(movies, this)
        // Attach the adapter to the recyclerview to populate items
        recMovieList.adapter = adapter
        // Set Layout manager to position the items
        recMovieList.layoutManager = LinearLayoutManager(this)
        // Thats all...?
        recMovieList.setHasFixedSize(true)


    }

    override fun displayDetails(position: Int) {

        val selection = movies[position]

        val intent = Intent(this, DisplayDetailsActivity::class.java)

        intent.putExtra("details", selection)

        startActivity(intent)
        onPause()
    }

}


