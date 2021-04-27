package com.gilsoncoding.ad340_project


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MoviesAdapter(private val movies: Array<Array<String>>, private val listener: OnItemClickLister): RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        
        
            val titleText: TextView = itemView.findViewById(R.id.title)
            val yearText: TextView = itemView.findViewById(R.id.year)



            init {
                itemView.setOnClickListener(this)
            }

            override fun onClick(v: View?) {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.displayDetails(position)
                }
            }
        }

   


    
    interface OnItemClickLister {
        fun displayDetails(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.item_row, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }
    
    override fun onBindViewHolder(holder: MoviesAdapter.ViewHolder, position: Int) {
        // Get the data model based on position

        val movie = movies[position]
        // Set item views based on your views and data model




        val titleText = holder.titleText
        val yearText = holder.yearText
        //val dirText = viewHolder.dirText
        titleText.text = movie[0]
        yearText.text = movie[1]


    }

    override fun getItemCount(): Int {
        return movies.size
    }


}

/*private fun AdapterView.OnItemClickListener.onItemClick(position: Int) {

        DisplayMoviesActivity.onItemClick(position)

}*/

