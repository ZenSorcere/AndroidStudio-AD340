package com.gilsoncoding.ad340_project

//import android.os.Handler
//import android.os.Looper
//import androidx.lifecycle.whenCreated
import android.Manifest
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext


class DisplayMapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    val LOCATION_REQ = 101
    var locationPermissionGranted = false
    private var lastKnownLocation: Location? = null
    lateinit var map: GoogleMap
    lateinit var locHeader: TextView
    var dataUrl = "https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=13&type=2"
    val cameraData: MutableList<Camera> = ArrayList()
    //private lateinit var locationCallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getDeviceLocation()
        checkForPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION, "location", LOCATION_REQ)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        //Attempt to delay OnMapReady function
            //Looper.myLooper()?.let { Handler(it) }?.postDelayed({ mapFragment?.getMapAsync(this) }, 5000)

        // Attempt to delay start of loadCameraLocs, since it calls the Markers function
            //Looper.myLooper()?.let { Handler(it) }?.postDelayed({ loadCameraLocs(dataUrl) }, 10000)
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
                            .addOnSuccessListener { location: Location? ->
                                // Got last known location. In some rare situations this can be null.
                                lastKnownLocation = location
                                Log.d("fused-per: ", lastKnownLocation.toString())
                            }
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        fun innerCheck(name: String) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "$name permissionRes refused", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(applicationContext, "$name permissionRes granted", Toast.LENGTH_SHORT).show()
                locationPermissionGranted = true
            }
            return
        }
        when (requestCode) {
            LOCATION_REQ -> innerCheck("location")
        }
    }

    // add a "my location button" on the map
    private fun enableMyLocation() {
        if (!::map.isInitialized) return
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            //checkForPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION, "location", LOCATION_REQ)
        }
    }

    // method that is supposed to get location from the device, but I can't get onMapReady to wait till it's completed
    private fun getDeviceLocation () {
        Log.d("FUNC: ", "getDevLoc start")
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.d("FUNC: ", "getDevLoc perm granted")
                val locationsResult = fusedLocationClient.lastLocation
                locationsResult.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("FUNC: ", "getDevLoc task successful")
                        lastKnownLocation = task.result
                        Log.d("Dev-lastknownLoc: ", lastKnownLocation.toString())
                        if (lastKnownLocation != null) {
                            Log.d("FUNC: ", "getDevLoc lastKnown not null")
                            map?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    LatLng(lastKnownLocation!!.latitude,
                                            lastKnownLocation!!.longitude), 13F
                            ))
                        }

                        } else {
                        Log.d("FUNC: ", "getDevLoc task FAIL")
                        Log.d(TAG, "Current location is null. Using defaults.")
                        Log.e(TAG, "Exception: %s", task.exception)
                        map?.moveCamera(CameraUpdateFactory
                                .newLatLngZoom(LatLng(0.0, 0.0), 8F))
                        map?.uiSettings?.isMyLocationButtonEnabled = false
                        Log.d("DevLoc-Else: ", lastKnownLocation.toString())
                    }
                    }
                }

            } catch (e: SecurityException) {
                Log.e("Exception: %s", e.message, e)
        }
    }



    override fun onMapReady(googleMap: GoogleMap?) {
        Log.d("LOCATION", "onMapReady")
        map = googleMap?: return
        enableMyLocation()
        map.uiSettings.isZoomControlsEnabled = true;


        Log.d("onMap-lastknownLoc: ", lastKnownLocation.toString())
        updateMap(googleMap)
        Log.d("FUNC: ", "onMapReady updateMap called")

    }

    // removed actual map update from onMapReady, thinking I might need to call map updates
    //   more than once, but I get rewrite errors
    fun updateMap (googleMap: GoogleMap?) {
        Log.d("FUNC: ", "updateMap start")

        // Initially had an if statement based on lastKnownLocation being set in a timely fashion
        //if (lastKnownLocation != null) {
            googleMap?.apply {
                //had to hard code the starting location, since getDeviceLocation wasn't happening quick enough
                val park = LatLng(47.645730,-122.334480)
                //val park = LatLng(lastKnownLocation!!.latitude, lastKnownLocation!!.longitude)
                addMarker(
                        MarkerOptions()
                                .position(park) //currentLoc
                                .title("LastKnownLocation: Gasworks")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                                .zIndex(1F)

                )
                moveCamera(CameraUpdateFactory.newLatLngZoom(park, 13F))

                // updating Text View with location information --attempting to reference the
                //   Marker title proved challenging
                var locHeader = findViewById<TextView>(R.id.LocationText)
                //locHeader.text = map.addMarker(MarkerOptions(title.toString()))
                """${park.latitude}, ${park!!.longitude}""".also { locHeader.text = it }

                Log.d("FUNC: ", "gasworks marker")
            }

        // Else statement for the knownlocation if statement
        /*}
        else {
            googleMap?.apply {
                //get current location LatLng coords instead of Sydney
                val defaultLoc = LatLng(0.0, 0.0)
                addMarker(
                        MarkerOptions()
                                .position(defaultLoc) //currentLoc
                                .title("Default Mark")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
                                .zIndex(1F)
                )
                moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLoc, 15F))
                Log.d("OMR-else: ", lastKnownLocation.toString())
                Log.d("FUNC: ", "Africa marker")
                //map.setMinZoomPreference(12F)
            }
        } */
    } //end of updateMap

    fun loadCameraLocs(dataUrl: String?) {
        Log.d("DATA", "loadCameraLocs")

        val queue = Volley.newRequestQueue(this)
        Log.d("VOLLEY", "queue created")

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
                        cameraData.add(camera)

                    }
                    showMarkers();

                }) { error -> Log.d("JSON", "Error: " + error.message) }

        queue.add(jsonReq)
    }


    fun showMarkers() {
        Log.d("DATA", cameraData.toString())
        for (i in 0 until cameraData.size)
        {
            val c = cameraData[i]
            //println(c)
            val position = LatLng(c.coords[0], c.coords[1])
            //println(position)
            val m = map.addMarker(
                    MarkerOptions()
                            .position(position)
                            .title(c.Description)
                            //.snippet(c.ImageUrl)
            )
            m.tag = i
        }
    }

    // Location updates related code
    /*override fun onResume() {
        super.onResume()
        if (requestingLocationUpdates) startLocationUpdates()
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper())
    }*/

} // End Display Map Activity


