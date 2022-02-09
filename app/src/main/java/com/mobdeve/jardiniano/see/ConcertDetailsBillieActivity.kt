package com.mobdeve.jardiniano.see

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mobdeve.jardiniano.see.databinding.ActivityConcertDetailsBillieBinding
import com.mobdeve.jardiniano.see.databinding.ActivityConcertDetailsHarryBinding


class ConcertDetailsBillieActivity : AppCompatActivity() {

    var binding: ActivityConcertDetailsBillieBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConcertDetailsBillieBinding.inflate(layoutInflater)
//        setContentView(R.layout.activity_concert_details_harry)
        setContentView(binding!!.root)

        binding!!.backButton.setOnClickListener {
            startActivity(
                Intent(
                    this@ConcertDetailsBillieActivity,
                    MainActivity::class.java
                )
            )

        }
    }
}