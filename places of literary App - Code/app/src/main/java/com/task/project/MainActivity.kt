package com.task.project

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.task.project.get.RetroClient
import com.task.project.model.Location
import com.task.project.utils.InternetConnection
import com.task.project.utils.InternetConnection.colors
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {
    private var  locations: ArrayList<Location> = ArrayList()


    private val colors2 = arrayOf<String>()
    var client: FusedLocationProviderClient? = null
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var mMap: GoogleMap? = null

    //String url = "http://maps.google.com/maps/api/staticmap?center=" + latitude + "," + longitude + "&zoom=15&size=300x300&sensor=false&key=YOUR_API_KEY"
    private val callback = OnMapReadyCallback { googleMap: GoogleMap? -> }
    var supportMapFragment: SupportMapFragment? = null

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadPlacesData()

        //Initialize fused Location
        client = LocationServices.getFusedLocationProviderClient(this)
        supportMapFragment = supportFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment?
        //check permission
        if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //when permission grated
            //Call method
        } else {
            //when permission denied
            //Request permission
            ActivityCompat.requestPermissions(this, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
            ), 44)
        }
    }
    //Initialize task Location
    val currentLocation: Unit
        get() {
            //Initialize task Location
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return
            }
            val task = client!!.lastLocation
            task.addOnSuccessListener { location ->
                //when
                if (location != null) {
                    for (i in locations.indices) {
                        val locationItem = locations[i]

                        //Sync map

                        val finalI1 = i
                        supportMapFragment!!.getMapAsync { googleMap ->
                            mMap = googleMap
                            //Initialize lat lng

                            val latLng = LatLng(locationItem!!.latitude!!.toDouble(), locationItem.longitude!!.toDouble())

                            googleMap.addMarker(MarkerOptions().position(latLng).title(locationItem.name)
                                    .snippet("Gaelic Name: " + locationItem.gaelic_name) // .icon(BitmapDescriptorFactory.defaultMarker(finalI1)))
                                    .icon(getMarkerIcon(colors[Random().nextInt(colors.size)])))?.tag = locationItem.name



                            //Create marker options
                            //
                            //Zoom Map
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                            //Add marker on Map
                            googleMap.setOnMarkerClickListener { marker: Marker ->
                                for (i in locations.indices) {
                                    if (locations[i]!!.name == marker.tag) {
                                        currentLocationItem = locations[i]
                                        currentLocationID = i
                                        DetailsLocationFragment().show(supportFragmentManager, "Details_Location_Fragment_TAG")
                                        break
                                    }
                                }
                                false
                            }


                        }
                    }
                }
            }
        }

    private fun initLocation() {
        val locationRequest = LocationRequest()
        locationRequest.smallestDisplacement = 10f
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 3000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        //
        val locationCallback: LocationCallback = object : LocationCallback() {
            //
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                val newPosition = LatLng(52.46725519402389, -1.9296343564614031)
                if (mMap != null) mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(newPosition, 18f))
            }
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        fusedLocationProviderClient!!.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    // method definition
    fun getMarkerIcon(color: String?): BitmapDescriptor {
        val hsv = FloatArray(3)
        Color.colorToHSV(Color.parseColor(color), hsv)
        return BitmapDescriptorFactory.defaultMarker(hsv[0])
    }

    protected fun loadPlacesData() {
        /**
         * Checking Internet Connection
         */
        if (InternetConnection.checkConnection(applicationContext)) {
            val dialog: ProgressDialog
            /**
             * Progress Dialog for User Interaction
             */
            dialog = ProgressDialog(this@MainActivity)
            dialog.setTitle("Getting JSON data")
            dialog.setMessage("Please wait...")
            dialog.show()

            //Creating an object of our api interface
            val api = RetroClient.apiService

            /**
             * Calling JSON
             */
            val call = api.location
            /**
             * Enqueue Callback will be call when get response...
             */
            call!!.enqueue(object : Call<List<Location?>>, Callback<List<Location?>?> {

                override fun onResponse(call: Call<List<Location?>?>, response: Response<List<Location?>?>) {
                    //Dismiss Dialog
                    dialog.dismiss()
                    if (response.isSuccessful) {
                        /**
                         * Got Successfully
                         */
                        Toast.makeText(this@MainActivity, "Got Successfully ", Toast.LENGTH_SHORT).show()
                        Log.e("LOGGG", "onResponse: " + response.body()!![0]!!.id)
                        Log.e("LOGGG", "onResponse: " + response.body()!![1]!!.id)
                        Log.e("LOGGG", "onResponse: " + response.body()!![2]!!.id)
                        locations= (response.body() as ArrayList<Location>?)!!
                        // adapter.notifyDataSetChanged();
                        InternetConnection.locations =locations
                        currentLocation
                        // initLocation();
                    } else {
                        Toast.makeText(this@MainActivity, "Something going wrong!", Toast.LENGTH_SHORT).show()
                    }
                }

                /**
                 * Invoked when a network exception occurred talking to the server or when an unexpected
                 * exception occurred creating the request or processing the response.
                 *
                 * @param call
                 * @param t
                 */
                override fun onFailure(call: Call<List<Location?>?>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Failure! : not get Data", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }

                override fun clone(): Call<List<Location?>> {
                    TODO("Not yet implemented")
                }

                override fun execute(): Response<List<Location?>> {
                    TODO("Not yet implemented")
                }

                override fun enqueue(callback: Callback<List<Location?>>) {
                    TODO("Not yet implemented")
                }

                override fun isExecuted(): Boolean {
                    TODO("Not yet implemented")
                }

                override fun cancel() {
                    TODO("Not yet implemented")
                }

                override fun isCanceled(): Boolean {
                    TODO("Not yet implemented")
                }

                override fun request(): Request {
                    TODO("Not yet implemented")
                }


            })
        } else {
            Toast.makeText(this@MainActivity, "Check your internet connection!", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        var currentLocationItem: Location? = null
        private var currentLocationID = 0
    }
}