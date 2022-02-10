package com.mobdeve.jardiniano.see

import android.R.attr
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.mobdeve.jardiniano.see.databinding.ActivityConcertDetailsHarryBinding
import com.mobdeve.jardiniano.see.databinding.ActivityHomeBinding
import android.R.attr.data
import android.widget.ToggleButton
import android.R
import android.view.View


class ConcertDetailsHarryActivity : AppCompatActivity() {

    var binding: ActivityConcertDetailsHarryBinding? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConcertDetailsHarryBinding.inflate(layoutInflater)
//        setContentView(R.layout.activity_concert_details_harry)
        setContentView(binding!!.root)


//        getIntent().getStringExtra("Concert Picture")?.let { binding!!.concertImgDetails.setImageResource(it.toInt()) }


        binding!!.backButton.setOnClickListener {
            startActivity(
                Intent(
                    this@ConcertDetailsHarryActivity,
                    MainActivity::class.java
                )
            )

        }


        setCheckedChangedListener()


    }

    private fun setCheckedChangedListener(): Boolean {

        binding!!.btnConcertSubscribe.setOnCheckedChangeListener { buttonView, isChecked ->

            Toast.makeText(this, "Subscribed to concert!", Toast.LENGTH_SHORT).show()
//            startActivity(
//                Intent(
//                    this@ConcertDetailsHarryActivity,
//                    SubscribelistActivity::class.java
//                )
//            )
        }
        return true
    }
}



