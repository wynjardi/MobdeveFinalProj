package com.mobdeve.jardiniano.see

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mobdeve.jardiniano.see.databinding.ActivityConcertDetailBinding

class ConcertDetailActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding:ActivityConcertDetailBinding

    //concert id
    private var concertId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConcertDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get concert id from intent
        concertId = intent.getStringExtra("concertId")!!

        //

        loadConcertDetails()

        //handle back button click and go back
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }

    private fun loadConcertDetails() {
        // concerts > concertId > details
        val ref = FirebaseDatabase.getInstance().getReference("Concerts")
        ref.child(concertId)
            .addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    //get data
                    val categoryId = "${snapshot.child("categoryId").value}"
                    val description = "${snapshot.child("description").value}"
                    val timestamp = "${snapshot.child("timestamp").value}"
                    val title = "${snapshot.child("title").value}"
                    val uid = "${snapshot.child("uid").value}"

                    //format date code to change and insert

                    //set data
                    binding.titleTv.text = title
                    binding.descriptionTv.text = description
                    //binding.dateTv.text = date
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
}