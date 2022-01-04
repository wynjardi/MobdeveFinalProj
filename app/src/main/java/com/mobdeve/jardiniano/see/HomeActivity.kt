package com.mobdeve.jardiniano.see

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.mobdeve.jardiniano.see.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    var username: TextView? = null
    var btnplay : Button? = null
    var binding: ActivityHomeBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val bundle = intent.extras

        binding!!.btnReg.setOnClickListener{
            startActivity(
                Intent(
                    this@HomeActivity,
                    MainActivity::class.java
                )
            )

        }

        binding!!.btnLogIn.setOnClickListener{
            startActivity(
                Intent(
                    this@HomeActivity,
                    Login::class.java
                )
            )

        }
    }


}