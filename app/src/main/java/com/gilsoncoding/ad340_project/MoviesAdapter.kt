package com.gilsoncoding.ad340_project


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

class MoviesAdapter (private val mContacts: List<MovieModel>): RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

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
        val movie: MovieModel = mContacts.get(position)
        // Set item views based on your views and data model
        val titleTextView = viewHolder.titleText
        titleTextView.text = movie.title
        val yearTextView = viewHolder.yearText
        yearTextView.text = movie.year
        //val dirTextView = viewHolder.dirText
        //dirTextView.text = movie.director
    }

    override fun getItemCount(): Int {
        return mContacts.size
    }
}
/*
internal class MoviesAdapter(private var movies: Array<MovieModel>) :
    RecyclerView.Adapter<MoviesAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title)
        var year: TextView = view.findViewById(R.id.year)
        var director: TextView = view.findViewById(R.id.director)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_display_movies, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = movies[position]
        holder.title.text = movie.getTitle()
        holder.year.text = movie.getYear()
        holder.director.text = movie.getDirector()
    }
    override fun getItemCount(): Int {
        return movies.size
    }
}*/
