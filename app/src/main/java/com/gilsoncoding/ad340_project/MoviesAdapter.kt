package com.gilsoncoding.ad340_project


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

class MoviesAdapter (private val movies: Array<Array<String>>): RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val titleText: TextView = itemView.findViewById<TextView>(R.id.title)
        val yearText = itemView.findViewById<TextView>(R.id.year)
        //val dirText = itemView.findViewById<TextView>(R.id.director)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.item_row, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }
    
    override fun onBindViewHolder(viewHolder: MoviesAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val movie = movies[position]
        // Set item views based on your views and data model

        val titleText = viewHolder.titleText
        val yearText = viewHolder.yearText
        //val dirText = viewHolder.dirText
        titleText.text = movie[0]
        yearText.text = movie[1]

        //val dirTextView = viewHolder.dirText
        //dirTextView.text = movie.director
    }

    override fun getItemCount(): Int {
        return movies.size
    }


}

