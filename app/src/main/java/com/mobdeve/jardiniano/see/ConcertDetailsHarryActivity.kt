package com.mobdeve.jardiniano.see

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.mobdeve.jardiniano.see.databinding.ActivityConcertDetailsHarryBinding

class ConcertDetailsHarryActivity : AppCompatActivity() {

    var binding: ActivityConcertDetailsHarryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_concert_details_harry)


//        getIntent().getStringExtra("Concert Picture")?.let { binding!!.concertImgDetails.setImageResource(it.toInt()) }


        binding!!.backButton.setOnClickListener {
            startActivity(
                Intent(
                    this@ConcertDetailsHarryActivity,
                    MainActivity::class.java
                )
            )

        }
    }
}