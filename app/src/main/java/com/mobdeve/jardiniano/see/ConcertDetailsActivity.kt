package com.mobdeve.jardiniano.see

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mobdeve.jardiniano.see.databinding.ActivityConcertDetailsBinding

class ConcertDetailsActivity : AppCompatActivity() {

    var binding: ActivityConcertDetailsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_concert_details)

        binding!!.backbutton.setOnClickListener {
            startActivity(
                Intent(
                    this@ConcertDetailsActivity,
                    MainActivity::class.java
                )
            )

        }
    }
}