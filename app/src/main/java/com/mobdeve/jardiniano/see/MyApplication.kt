package com.mobdeve.jardiniano.see

import android.app.Application
import android.text.format.DateFormat
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storageMetadata
import java.sql.Timestamp
import java.util.*

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }

    companion object{

        //convert timestamp to date and use it globally
        fun formatTimeStamp(timestamp: Long) : String{

            val cal = Calendar.getInstance(Locale.ENGLISH)
            cal.timeInMillis = timestamp

            return DateFormat.format("dd/MM/yyyy",cal).toString()
        }

        //fun for pic thumbnail
        fun loadConcertFromUrlSinglePage(
            imgUrl: String,
            concertTitle: String,
            imageView: ImageView,
            progressBar: ProgressBar,


            ){
            val TAG = "CONCERT_THUMBNAIL_TAG"

            //get gfrom url and from firebase storage
            val ref = FirebaseStorage.getInstance().getReferenceFromUrl(imgUrl)
            ref.metadata
                .addOnSuccessListener { storageMetadata ->
                    Log.d(TAG, "Got metadata")

                }

        }

        fun loadCategory (categoryId: String, categoryTv: TextView){
            val ref = FirebaseDatabase.getInstance().getReference("Categories")
            ref.child(categoryId)
                .addListenerForSingleValueEvent(object: ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {

                        val category= "${snapshot.child("category").value}"

                        //set categ
                        categoryTv.text = category
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
        }

    }


}