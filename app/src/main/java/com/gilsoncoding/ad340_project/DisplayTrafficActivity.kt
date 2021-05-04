package com.gilsoncoding.ad340_project


import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley


class DisplayTrafficActivity : AppCompatActivity() {


    lateinit var cameraList: ListView


    var dataUrl = "https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=13&type=2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_traffic)
        checkNetworkConnection()

        val cameraData: MutableList<Camera> = ArrayList()

        val queue = Volley.newRequestQueue(this)
        val adapter = TrafficAdapter(cameraData)
        val recCameraList = findViewById<RecyclerView>(R.id.recCameraList)
        recCameraList.adapter = adapter
        recCameraList.layoutManager = LinearLayoutManager(this)
        recCameraList.setHasFixedSize(true)

        val jsonReq = JsonObjectRequest(Request.Method.GET, dataUrl, null,
                { response ->

                    // Log.d("camera listings", response.toString())
                    val locations = response.getJSONArray("Features")
                    for (i in 1 until locations.length()) {


                        val cameras = locations.getJSONObject(i).getJSONArray("Cameras")

                        val camera = Camera(

                                cameras.getJSONObject(0).getString("Description"),
                                cameras.getJSONObject(0).getString("ImageUrl"),
                                cameras.getJSONObject(0).getString("Type")
                        )
                        // Log.d("desc", camera.toString())
                        cameraData.add(camera)
                    }
                    adapter.notifyDataSetChanged()


                }) { error -> Log.d("JSON", "Error: " + error.message) }

        queue.add(jsonReq)

    }


    private fun checkNetworkConnection() {
        val connectivityManager = getSystemService(ConnectivityManager::class.java)
        val currentNetwork = connectivityManager.activeNetwork
        val caps = connectivityManager.getNetworkCapabilities(currentNetwork)
        val linkProperties = connectivityManager.getLinkProperties(currentNetwork)
        val status = caps?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        if (status == true) {
            // attempt to move volley request queue code here resulted in activity crashing

        } else {
            Toast.makeText(applicationContext, "No Internet!!", Toast.LENGTH_LONG).show()
        }

    } // end checkNetworkConnection

}

