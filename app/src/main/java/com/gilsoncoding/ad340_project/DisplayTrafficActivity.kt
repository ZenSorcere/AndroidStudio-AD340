package com.gilsoncoding.ad340_project

import android.app.DownloadManager
import android.content.ContentValues.TAG
import android.content.Context
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
//import retrofit2.Retrofit
//import retrofit2.*
// import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.awaitResponse


class DisplayTrafficActivity : AppCompatActivity() {

    //private val wifiConnected = false
    //private val mobileConnected = false

    lateinit var cameraList: ListView
    //lateinit var listAdapter:CameraListAdapter

    var dataUrl = "https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=13&type=2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_traffic)
        checkNetworkConnection()

        val cameraData : MutableList<Camera> = ArrayList()

        val queue = Volley.newRequestQueue(this)
        val adapter = TrafficAdapter(cameraData)
        val recCameraList = findViewById<RecyclerView>(R.id.recCameraList)
        recCameraList.adapter = adapter
        recCameraList.layoutManager = LinearLayoutManager(this)
        // Thats all...?
        recCameraList.setHasFixedSize(true)

        val jsonReq = JsonObjectRequest(Request.Method.GET, dataUrl, null,
            { response ->

                Log.d("camera listings", response.toString())
                val locations = response.getJSONArray("Features")
                for (i in 1 until locations.length()) {


                    val cameras = locations.getJSONObject(i).getJSONArray("Cameras")

                    val camera = Camera(
                        //camera.getString("Description")
                        cameras.getJSONObject(0).getString("Description"),
                        cameras.getJSONObject(0).getString("ImageUrl"),
                        cameras.getJSONObject(0).getString("Type")
                        )
                    Log.d("desc", camera.toString())
                    cameraData.add(camera)
                }


                // textView.text = "That didn't work!"
            }) { error -> Log.d("JSON", "Error: " + error.message) }

// Add the request to the RequestQueue.
        queue.add(jsonReq)

    }

        //val context = applicationContext
        //val duration = Toast.LENGTH_SHORT
        private fun checkNetworkConnection() {
            val connectivityManager = getSystemService(ConnectivityManager::class.java)
            val currentNetwork = connectivityManager.activeNetwork
            val caps = connectivityManager.getNetworkCapabilities(currentNetwork)
            val linkProperties = connectivityManager.getLinkProperties(currentNetwork)
            val status = caps?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            if (status == true) {
                //Toast.makeText(context, status.toString(), duration).show()

            } else {
               // Toast.makeText(context, "No Internet!", duration).show()
            }


        /* if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network : Network) {
                    Log.e(TAG, "The default network is now: " + network)

                }

                override fun onLost(network : Network) {
                    Log.e(TAG, "The application no longer has a default network. The last default network was " + network)

                }

                override fun onCapabilitiesChanged(network : Network, networkCapabilities : NetworkCapabilities) {
                    Log.e(TAG, "The default network changed capabilities: " + networkCapabilities)

                }

                override fun onLinkPropertiesChanged(network : Network, linkProperties : LinkProperties) {
                    Log.e(TAG, "The default network changed link properties: " + linkProperties)

                }
            })
        } // end networkCallback */
        } // end checkNetworkConnection


// ...

    // Instantiate the RequestQueue.


        /* val retrofit = Retrofit.Builder()
                .baseUrl(dataUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(someinterface::class.java) */

        /*val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(Toolbar)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true)
        cameraList = findViewById(R.id.cameraList)
        listAdapter = CameraListAdapter(this, cameraData)
        cameraList.setAdapter(listAdapter) */

        // check network status
        //val context = applicationContext
        //val duration = Toast.LENGTH_SHORT
        //val text = mobileConnected.toString()
        /*if (wifiConnected || mobileConnected)

        {
            // initiate data request
            //loadCameraData(dataUrl)


            val btntext = Toast.makeText(context, "btn"+ text, duration).show()
        }
        else
        {
            val error = Toast.makeText(context, "error" + text, duration).show()
        } */

        /*val textView = findViewById<TextView>(R.id.text)
        // ...

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://www.google.com"

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                // Display the first 500 characters of the response string.
                textView.text = "Response is: ${response.substring(0, 500)}"
            },
            { textView.text = "That didn't work!" })

// Add the request to the RequestQueue.
        queue.add(stringRequest)*/
    }

    /* class CameraListAdapter(context: Context, values:ArrayList<TrafficCamera>): ArrayAdapter<TrafficCamera>(context, 0, values) {
        private val context:Context
        private val values:ArrayList<TrafficCamera>
        init{
            this.context = context
            this.values = values
        }
    } */

    /* fun loadCameraData(dataUrl:String) {
        val textView = findViewById<TextView>(R.id.text)
        val queue = Volley.newRequestQueue(this)
        val url = dataUrl

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null,
            { response ->
                textView.text = "Response: %s".format(response.toString())
            },
            { error ->
                // TODO: Handle Error
            }
        )
    } */


   /* public void loadCameraData(String dataUrl) {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonReq = new JsonArrayRequest
        (Request.Method.GET, dataUrl, null,  )
    }*/



   // class ExampleFragment : Fragment(R.layout.example_fragment)
