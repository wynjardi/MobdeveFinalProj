package com.mobdeve.jardiniano.see

import android.content.ContentProviderClient
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.gms.location.*
import com.mobdeve.jardiniano.see.databinding.ActivitySubscribelistBinding
import java.util.jar.Manifest

class SubscribelistActivity : AppCompatActivity() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    val PERMISSION_ID = 1000


    lateinit var binding: ActivitySubscribelistBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubscribelistBinding.inflate(layoutInflater)

        setContentView(binding!!.root)

        NavBar(findViewById<BottomNavigationView>(R.id.bottom_nav), this, R.id.subscribeIcon)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        binding!!.getLocation.setOnClickListener {
            Log.d("Debug:",checkPermission().toString())
            Log.d("Debug:",isLocationEnabled().toString())
            requestPermission()
            /* fusedLocationProviderClient.lastLocation.addOnSuccessListener{location: Location? ->
                 textView.text = location?.latitude.toString() + "," + location?.longitude.toString()
             }*/
//            getLastLocation()
        }

    }




    private fun checkPermission():Boolean{
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ){
            return true
        }

        return false
    }

    private fun requestPermission(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION),PERMISSION_ID
        )
    }

    private fun isLocationEnabled():Boolean{

        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == PERMISSION_ID){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Debug", "You have permission")
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

//    fun getLastLocation(){
//        if(checkPermission()){
//            if(isLocationEnabled()){
//                fusedLocationProviderClient.lastLocation.addOnCompleteListener {task->
//                    var location:Location? = task.result
//                    if(location == null){
//                        NewLocationData()
//                    }else{
//                        Log.d("Debug:" ,"Your Location:"+ location.longitude)
//                        text.text = "You Current Location is : Long: "+ location.longitude + " , Lat: " + location.latitude + "\n" + getCityName(location.latitude,location.longitude)
//                    }
//                }
//            }else{
//                Toast.makeText(this,"Please Turn on Your device Location",Toast.LENGTH_SHORT).show()
//            }
//        }else{
//            requestPermission()
//        }
//    }

//    fun NewLocationData(){
//        var locationRequest =  LocationRequest()
//        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//        locationRequest.interval = 0
//        locationRequest.fastestInterval = 0
//        locationRequest.numUpdates = 1
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
//        fusedLocationProviderClient!!.requestLocationUpdates(
//            locationRequest,locationCallback,Looper.myLooper()
//        )
//    }
//
//
//    private val locationCallback = object : LocationCallback(){
//        override fun onLocationResult(locationResult: LocationResult) {
//            var lastLocation: Location = locationResult.lastLocation
//            Log.d("Debug:","your last last location: "+ lastLocation.longitude.toString())
//            textView.text = "You Last Location is : Long: "+ lastLocation.longitude + " , Lat: " + lastLocation.latitude + "\n" + getCityName(lastLocation.latitude,lastLocation.longitude)
//        }
//    }
}

