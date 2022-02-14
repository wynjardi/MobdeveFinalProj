package com.mobdeve.jardiniano.see

import android.app.Application
import android.text.format.DateFormat
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
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

    }

}