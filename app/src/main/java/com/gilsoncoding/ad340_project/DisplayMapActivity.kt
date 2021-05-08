package com.gilsoncoding.ad340_project

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior.setTag
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.VolleyLog.setTag
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class DisplayMapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    val LOCATION_REQ = 101
    var locationPermissionGranted = false
    lateinit var map: GoogleMap
    lateinit var mapView: MapView
    //lateinit var locHeader: TextView
    var dataUrl = "https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=13&type=2"
    val cameraData: MutableList<Camera> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        checkForPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION, "location", LOCATION_REQ)


        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        /*inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
            val locText: TextView = itemView.findViewById(R.id.LocationText)
        }*/

        loadCameraLocs(dataUrl)
    }

    // From youtube video
    private fun checkForPermissions(permission: String, name: String, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(applicationContext, permission) == PackageManager.PERMISSION_GRANTED -> {
                    Toast.makeText(applicationContext, "$name permissioncheck granted", Toast.LENGTH_SHORT).show()
                    locationPermissionGranted = true
                    fusedLocationClient.lastLocation
                            /*.addOnSuccessListener { location : Location? ->
                                // Got last known location. In some rare situations this can be null.
                            }*/
                }
                shouldShowRequestPermissionRationale(permission) -> showDialog(permission, name, requestCode)

                else -> ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            }
        }
    }



    private fun showDialog(permission: String, name: String, requestCode: Int) {
        val builder = AlertDialog.Builder(this)

        builder.apply {
            setMessage("Permission to access your $name is required to use this app")
            setTitle("Permission required")
            setPositiveButton("OK") { dialog, which ->
                ActivityCompat.requestPermissions(this@DisplayMapActivity, arrayOf(permission), requestCode)
            }
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        locationPermissionGranted = false
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        fun innerCheck(name: String) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "$name permissionRes refused", Toast.LENGTH_SHORT).show()
                locationPermissionGranted = true
            } else {
                Toast.makeText(applicationContext, "$name permissionRes granted", Toast.LENGTH_SHORT).show()
            }
            return
        }
        when (requestCode) {
            LOCATION_REQ -> innerCheck("location")
        }

    }
    private fun enableMyLocation() {
        if (!::map.isInitialized) return
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map?.isMyLocationEnabled = true
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            checkForPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION, "location", LOCATION_REQ)
        }
    }



    override fun onMapReady(googleMap: GoogleMap?) {
        Log.d("LOCATION", "onMapReady")
        //mapView.onResume()
        map = googleMap ?: return
        enableMyLocation()
        /*var home =
        googleMap.apply {
            moveCamera(CameraUpdateFactory.newLatLng(myLocation))
        */
        //map.
        //updateLocationUI()
        //getDeviceLocation()

    // I can't get either of these options to do anything--
        // OPTION A:
        /*googleMap?.addMarker(
                MarkerOptions()
                        .position(LatLng(0.0, 0.0))
                        .title("Test Marker")
        )*/

        // OPTION B:
        /*googleMap?.apply {
            //get current location LatLng coords instead of Sydney
            val sydney = LatLng(-33.852, 151.211)
            addMarker(
                MarkerOptions()
                    .position(sydney) //currentLoc
                    .title("Marker in Sydney")
            )
            // [START_EXCLUDE silent]
            moveCamera(CameraUpdateFactory.newLatLng(sydney))
            // [END_EXCLUDE]
        }*/
    }

    fun loadCameraLocs(dataUrl: String?) {
        Log.d("DATA", "loadCameraLocs")
        //val cameraData: MutableList<Camera> = ArrayList()

        val queue = Volley.newRequestQueue(this)
        Log.d("VOLLEY", "queue created")
        //val adapter = TrafficAdapter(cameraData)
        //val recCameraList = findViewById<RecyclerView>(R.id.recCameraList)
       // recCameraList.adapter = adapter
        //recCameraList.layoutManager = LinearLayoutManager(this)
        //recCameraList.setHasFixedSize(true)

        val jsonReq = JsonObjectRequest(Request.Method.GET, dataUrl, null,
                { response ->

                    Log.d("camera listings", response.toString())
                    val locations = response.getJSONArray("Features")
                    for (i in 1 until locations.length()) {


                        val cameras = locations.getJSONObject(i).getJSONArray("Cameras")
                        val pc = locations.getJSONObject(i)
                        val pointCoordinates = pc.getJSONArray("PointCoordinate")
                        val latLngs = doubleArrayOf(pointCoordinates.getDouble(0), pointCoordinates.getDouble(1))
                        val camera = Camera(

                                cameras.getJSONObject(0).getString("Description"),
                                cameras.getJSONObject(0).getString("ImageUrl"),
                                cameras.getJSONObject(0).getString("Type"),
                                latLngs
                        )
                        // Log.d("desc", camera.toString())
                        cameraData.add(camera)

                    }
                    showMarkers();
                    //adapter.notifyDataSetChanged()


                }) { error -> Log.d("JSON", "Error: " + error.message) }

        queue.add(jsonReq)
    }
    fun showMarkers() {
        Log.d("DATA", cameraData.toString())
        for (i in 0 until cameraData.size)
        {
            val c = cameraData[i]
            println(c)
            val position = LatLng(c.coords[0], c.coords[1])
            println(position)
            val m = map.addMarker(
                    MarkerOptions()
                    .position(position)
                    .title(c.Description)
                    .snippet(c.ImageUrl)
            )
            println(m)
            m.tag = i
        }
    }

}


