package com.mobdeve.jardiniano.see

import android.annotation.SuppressLint
import android.content.ContentProviderClient
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.os.AsyncTask
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.mobdeve.jardiniano.see.databinding.ActivitySubscribelistBinding
import java.util.*
import java.util.jar.Manifest
import kotlin.collections.ArrayList

class SubscribelistActivity : AppCompatActivity() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    val PERMISSION_ID = 1000

    lateinit var binding: ActivitySubscribelistBinding

    //for database
    private lateinit var firebaseAuth: FirebaseAuth

    //
    private lateinit var adapterConcertSubscription: AdapterConcertSubscription

    // arraylist to hold concerts
    private lateinit var concertArrayList: ArrayList<ModelConcert>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubscribelistBinding.inflate(layoutInflater)

        setContentView(binding!!.root)
//        val textView = findViewById<TextView?>(R.id.my_location)
        loadSubscribeConcerts()


        NavBar(findViewById<BottomNavigationView>(R.id.bottom_nav), this, R.id.subscribeIcon)

//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

//        binding!!.getLocation.setOnClickListener {
//            Log.d("Debug:",checkPermission().toString())
//            Log.d("Debug:",isLocationEnabled().toString())
//            requestPermission()
//            /* fusedLocationProviderClient.lastLocation.addOnSuccessListener{location: Location? ->
//                 textView.text = location?.latitude.toString() + "," + location?.longitude.toString()
//             }*/
////            getLastLocation()
//        }

    }

    private fun loadSubscribeConcerts(){

        //init arraylist
        concertArrayList = ArrayList();

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseAuth.uid!!).child("Subscriptions")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    //clear arraylist before adding
                    concertArrayList.clear()
                    for (ds in snapshot.children){
                        //get id of the concerts
                        val concertId = "${ds.child("concertId").value}"

                        val modelConcert = ModelConcert()
                        modelConcert.id= concertId

                        concertArrayList.add(modelConcert)
                    }
                    //set up adapter
                    adapterConcertSubscription = AdapterConcertSubscription(this@SubscribelistActivity, concertArrayList)
                    //set adapter to recyclerviewr
                    binding.subscriptionsRv.adapter = adapterConcertSubscription

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }

}



//    private fun checkPermission():Boolean{
//        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
//            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
//        ){
//            return true
//        }
//
//        return false
//    }
//
//    private fun requestPermission(){
//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION),PERMISSION_ID
//        )
//    }
//
//    private fun isLocationEnabled():Boolean{
//
//        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
//    }
//
//
////
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        if(requestCode == PERMISSION_ID){
//            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                Log.d("Debug", "You have permission")
//            }
//        }
//
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }

//    private fun getCityName(lat: Double,long: Double):String{
//        var cityName:String = ""
//        var countryName = ""
//        var geoCoder = Geocoder(this, Locale.getDefault())
//        var Adress = geoCoder.getFromLocation(lat,long,3)
//
//        cityName = Adress.get(0).locality
//        countryName = Adress.get(0).countryName
//        Log.d("Debug:","Your City: " + cityName + " ; your Country " + countryName)
//        return cityName
//    }
////
//    fun getLastLocation(){
//    val textView = findViewById<TextView?>(R.id.my_location)
//        if(checkPermission()){
//            if(isLocationEnabled()){
//                fusedLocationProviderClient.lastLocation.addOnCompleteListener {task->
//                    var location:Location? = task.result
//                    if(location == null){
//                        NewLocationData()
//                    }else{
//                        Log.d("Debug:" ,"Your Location:"+ location.longitude)
//                        textView.setText( "You Current Location is : Long: "+ location.longitude + " , Lat: " + location.latitude + "\n" + getCityName(location.latitude,location.longitude)).toString()
//                    }
//                }
//            }else{
//                Toast.makeText(this,"Please Turn on Your device Location",Toast.LENGTH_SHORT).show()
//            }
//        }else{
//            requestPermission()
//        }
//    }
//////
//    @SuppressLint("MissingPermission")
//    fun NewLocationData(){
//        var locationRequest =  LocationRequest()
//        locationRequest.priority = PRIORITY_HIGH_ACCURACY
//        locationRequest.interval = 0
//        locationRequest.fastestInterval = 0
//        locationRequest.numUpdates = 1
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
//        fusedLocationProviderClient!!.requestLocationUpdates(
//            locationRequest,locationCallback, Looper.myLooper()
//        )
//    }
//////
//////
//    private val locationCallback = object : LocationCallback(){
//    val textView = findViewById<TextView?>(R.id.my_location)
//        override fun onLocationResult(locationResult: LocationResult) {
//            var lastLocation: Location = locationResult.lastLocation
//            Log.d("Debug:","your last last location: "+ lastLocation.longitude.toString())
//            textView.setText( "You Last Location is : Long: "+ lastLocation.longitude + " , Lat: " + lastLocation.latitude + "\n" + getCityName(lastLocation.latitude,lastLocation.longitude)).toString()
//        }
//
//    }


