package com.gilsoncoding.ad340_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DefaultItemAnimator
import android.widget.Button
import android.widget.TextView
import android.content.Intent
import android.net.Uri
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.gilsoncoding.ad340_project.R

class DisplayMoviesActivity : AppCompatActivity() {
    lateinit var movies: List<MovieModel>
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_movies)
        // Lookup the recyclerview in activity layout
        val recMovieList = findViewById<View>(R.id.recMovieList) as RecyclerView
        // Initialize movies
        movies = MovieModel.createMoviesList(20)
        // Create adapter passing in the sample user data
        val adapter = MoviesAdapter(movies)
        // Attach the adapter to the recyclerview to populate items
        recMovieList.adapter = adapter
        // Set Layout manager to position the items
        recMovieList.layoutManager = LinearLayoutManager(this)
        // Thats all...?


        // to display the clicked button (gButton1) text:
        //val intent = intent
        //val message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE)
        //val textView = findViewById<TextView>(R.id.textView7)
        //textView.text = message


    }
    // attempt to replicate class lecture--my ArrayList is of MovieModel object, not strings, tho
    /*fun showMovie(index:Int) {
        val intent = Intent(this, DisplayDetailsActivity::class.java)
        val b = Bundle()
        b.putStringArray(EXTRA_MESSAGE, movies[index])
        b.a(EXTRA_MESSAGE, movies[index])
    }*/
    // function to show the DisplayDetails Activity on button click
    fun displayDetails(view:View) {
        //contactUri: Uri) {
        //    val intent = Intent(Intent.ACTION_VIEW, contactUri)
        val intent = Intent(this, DisplayDetailsActivity::class.java)
        val movieText = view as TextView //movies[0]
        val message = movieText.text.toString()
    // This will pass and display first entry in the array's title, but how to change based on index?
        //val test = movies[0].title

        intent.putExtra(EXTRA_MESSAGE, message)
        //intent.putExtra(MOVIE_DETAILS, movies[0])

        startActivity(intent)
    }

    companion object {
        const val EXTRA_MESSAGE = "com.gilsoncoding.ad340_project.MESSAGE"
        //val MOVIE_DETAILS = "com.gilsoncoding.ad340_project.DETAILS"
    }
}