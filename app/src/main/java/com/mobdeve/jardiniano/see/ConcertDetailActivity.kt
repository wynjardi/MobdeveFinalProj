package com.mobdeve.jardiniano.see

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.mobdeve.jardiniano.see.databinding.ActivityConcertDetailBinding

class ConcertDetailActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding:ActivityConcertDetailBinding

    //concert id
    private var concertId = ""

    //holds boolean value fals/true
    private var isinSubscriptions = false
    //get from firebase
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConcertDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get concert id from intent
        concertId = intent.getStringExtra("concertId")!!
        Log.i("CONCERT DETAILS", concertId)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser != null){
            //user is logged in, check if subscribed or not
            checkIsSubscribed()
        }

        loadConcertDetails()

        //handle back button click and go back
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        //handle click subscribe button
        binding.subscribeBtn.setOnClickListener{
            if (isinSubscriptions){
                //already in subscribed, remove
                removeFromSubscriptions()
            }
            else
            // not in subscriptions, add it
                clickSubscribe()

        }
    }

    private fun loadConcertDetails() {
        // concerts > concertId > details
        val ref = FirebaseDatabase.getInstance().getReference("Concerts")
        ref.child(concertId)
            .addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.i("CONCERT DETAILS", snapshot.toString())

                    val concert = snapshot.getValue<ModelConcert>()

                    //set data
                    concert?.let {
                        Firebase.database.reference.child("Categories")
                            .child(concert.categoryId).child("category").get()
                            .addOnSuccessListener {
                                binding.categoryTv.text = it.getValue<String>()
                            }

                        binding.titleTv.text = concert.concertName
                        binding.dateTv.text = MyApplication
                            .formatTimeStamp(concert.timestamp)
                        binding.descriptionTv.text = concert.concertArtist

                        Glide.with(binding.imageView)
                            .load(concert.imageUrl)
                            .centerCrop()
                            .into(binding.imageView)
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun checkIsSubscribed(){

        Log.d(TAG, "checkIsSubscribed: Checking if subscribed to concert")

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseAuth.uid!!).child("Subscriptions").child(concertId)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    isinSubscriptions = snapshot.exists()
                    if (isinSubscriptions){
                        //present in subs
                        Log.d(TAG, "onDataChange: onDataChange: Available in subscriptions")
                        //set drawable icon
                        binding.subscribeBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ic_favorite_filled_white, 0, 0)
                        binding.subscribeBtn.text = "Remove Subscription"
                    }

                    else{
                        Log.d(TAG, "onDataChange: onDataChange: Not available in subscriptions")
                        binding.subscribeBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ic_favorite_filled_white, 0, 0)
                        binding.subscribeBtn.text = "Add Subscription"
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

    }

    private fun clickSubscribe(){
        Log.d(TAG, "clickSubscribe: Adding to subscriptions")
        val timestamp = System.currentTimeMillis()

        //setup data to add in db
        val hashMap = HashMap<String, Any>()
        hashMap["concertId"] = concertId
        hashMap["timestamp"] = timestamp

        //save to db
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseAuth.uid!!).child("Subscriptions").child(concertId)
            .setValue(hashMap)
            .addOnSuccessListener {
                //added to subs
                Log.d(TAG, "clickSubscribe: Added to subscriptions")
                Toast.makeText(this, "Add to subscriptions", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener{ e->
                //failed to add to subscriptions
                Log.d(TAG, "clickSubscribe: Failed to add to subscriptions due to ${e.message}")
                Toast.makeText(this, "Failed to ass to subscriptions due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun removeFromSubscriptions(){
        Log.d(TAG, "removeFromSubscriptions: Removing from subscriptions")

        //database ref
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseAuth.uid!!).child("Subscriptions").child(concertId)
            .removeValue()
            .addOnSuccessListener {
                Log.d(TAG, "removeFromSubscriptions: Remove from subscriptions")
                Toast.makeText(this, "Removed from subscriptions", Toast.LENGTH_SHORT).show()
            }

            .addOnFailureListener{ e->
                Log.d(TAG, "removeFromSubscriptions: Failed to remove from subscriptions due to ${e.message}")
                Toast.makeText(this, "Failed to remove from subscriptions due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

}