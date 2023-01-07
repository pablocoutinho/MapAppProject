package com.task.project.get

import retrofit2.Retrofit
import com.task.project.get.RetroClient
import retrofit2.converter.gson.GsonConverterFactory
import com.task.project.get.JSONPlaceholder
import retrofit2.http.GET
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import android.annotation.SuppressLint
import android.os.Bundle
import com.task.project.R
import com.google.android.gms.location.LocationServices
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.task.project.MainActivity
import com.task.project.DetailsLocationFragment
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import android.os.Looper
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.task.project.utils.InternetConnection
import android.app.ProgressDialog
import android.widget.Toast
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable

object RetroClient {
    /********
     * URLS
     */
    private const val ROOT_URL = "https://gist.githubusercontent.com/saravanabalagi/541a511eb71c366e0bf3eecbee2dab0a/raw/bb1529d2e5b71fd06760cb030d6e15d6d56c34b3/"

    /**
     * Get Retrofit Instance
     */
    private val retrofitInstance: Retrofit
        private get() = Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

    /**
     * Get API Service
     *
     * @return API Service
     */
    val apiService: JSONPlaceholder
        get() = retrofitInstance.create(JSONPlaceholder::class.java)
}