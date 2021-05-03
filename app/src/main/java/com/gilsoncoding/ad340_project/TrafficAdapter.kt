package com.gilsoncoding.ad340_project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

val imageUrlBase = "https://www.seattle.gov/trafficcams/images/"

class TrafficAdapter (private val cameraData: List<Camera>): RecyclerView.Adapter<TrafficAdapter.ViewHolder>() {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val camDesc: TextView = itemView.findViewById(R.id.tdesc)
        val camImg: ImageView = itemView.findViewById(R.id.timage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrafficAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.cam_row, parent, false)

        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tCam = cameraData[position]
        // Set item views based on your views and data model




        val camDesc = holder.camDesc
        val camImg = holder.camImg
        //val dirText = viewHolder.dirText
        camDesc.text = tCam.Description
        //camImg.
        val screenshot = imageUrlBase + tCam.ImageUrl
        Picasso.get().load(screenshot).into(camImg)

        // Get the data model based on position
        /*holder.camDesc.text = trafCam.Features[position].Cameras[0].Description
        holder.camImg.contentDescription = trafCam.Features[position].Cameras[0].Description
        val screenshot = imageUrlBase + trafCam.Features[position].Cameras[0].ImageUrl
        Picasso.get().load(screenshot).into(holder.camImg)*/
    }

    override fun getItemCount(): Int {
        //return trafCam.Features.size
        return 5
    }


}

