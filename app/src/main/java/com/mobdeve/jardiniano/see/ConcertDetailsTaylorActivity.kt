package com.mobdeve.jardiniano.see

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mobdeve.jardiniano.see.databinding.ActivityConcertDetailsHarryBinding
import com.mobdeve.jardiniano.see.databinding.ActivityConcertDetailsTaylorBinding


class ConcertDetailsTaylorActivity : AppCompatActivity() {

    var binding: ActivityConcertDetailsTaylorBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConcertDetailsTaylorBinding.inflate(layoutInflater)
//        setContentView(R.layout.activity_concert_details_harry)
        setContentView(binding!!.root)

        binding!!.backButton.setOnClickListener {
            startActivity(
                Intent(
                    this@ConcertDetailsTaylorActivity,
                    MainActivity::class.java
                )
            )

        }
    }
}