package com.mobdeve.jardiniano.see

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mobdeve.jardiniano.see.databinding.ActivityForumBinding
import com.mobdeve.jardiniano.see.databinding.ActivityProfileBinding

class ForumActvitity : AppCompatActivity() {

    lateinit var binding: ActivityForumBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForumBinding.inflate(layoutInflater)

        setContentView(binding.root)

        NavBar(findViewById<BottomNavigationView>(R.id.bottom_nav), this, R.id.chatIcon)
    }
}