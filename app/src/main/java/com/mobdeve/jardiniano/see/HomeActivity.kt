package com.mobdeve.jardiniano.see

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mobdeve.jardiniano.see.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    var binding: ActivityHomeBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.btnLogIn.setOnClickListener{
            startActivity(
                Intent(
                    this@HomeActivity,
                    MainActivity::class.java
                )
            )

        }

        binding!!.btnReg.setOnClickListener{
            startActivity(
                Intent(
                    this@HomeActivity,
                    MainActivity::class.java
                )
            )

        }
    }


}