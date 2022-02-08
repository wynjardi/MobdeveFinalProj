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
        setContentView(R.layout.activity_concert_details_harry)

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