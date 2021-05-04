package com.gilsoncoding.ad340_project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

val imageUrlBase = "https://www.seattle.gov/trafficcams/images/"
val imageUrlBase2 = "https://images.wsdot.wa.gov/nw/"

class TrafficAdapter (private val cameraData: MutableList<Camera>): RecyclerView.Adapter<TrafficAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val camDesc: TextView = itemView.findViewById(R.id.tdesc)
        val camImg: ImageView = itemView.findViewById(R.id.timage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrafficAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.cam_row, parent, false)

        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: TrafficAdapter.ViewHolder, position: Int) {
        val tCam = cameraData[position]

        val camDesc = holder.camDesc
        val camImg = holder.camImg
        camDesc.text = tCam.Description
        holder.camImg.contentDescription = camDesc.text

        var screenshot = ""
        screenshot = if (tCam.Type == "sdot") {
            imageUrlBase + tCam.ImageUrl
        } else {
            imageUrlBase2 + tCam.ImageUrl
        }
        Picasso.get().load(screenshot).into(camImg)

    }

    override fun getItemCount(): Int {
        return cameraData.count()
    }


}

