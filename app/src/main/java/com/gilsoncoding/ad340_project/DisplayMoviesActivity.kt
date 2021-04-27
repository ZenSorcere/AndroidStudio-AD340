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
    //fun displayDetails(view: View) {
    override fun displayDetails(position: Int) {

        val selection = movies[position]
        //val b = Bundle()
        val intent = Intent(this, DisplayDetailsActivity::class.java)
        //b.putStringArray(EXTRA_MESSAGE, selection)
        intent.putExtra("details", selection)

        startActivity(intent)
        onPause()
    }

    /*companion object {
        const val EXTRA_MESSAGE = "com.gilsoncoding.ad340_project.MESSAGE"
        //val MOVIE_DETAILS = "com.gilsoncoding.ad340_project.DETAILS"
    }*/

}


