package com.mobdeve.jardiniano.see

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.mobdeve.jardiniano.see.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityProfileBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure action bar 
        actionBar = supportActionBar!!
        actionBar.title = "Profile"

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //handle click, logout
        binding.btnLogout.setOnClickListener{
            firebaseAuth.signOut()
            checkUser()
        }

        NavBar(findViewById<BottomNavigationView>(R.id.bottom_nav), this, R.id.profileIcon)
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null){
            //user not null, user is logged in
            val email = firebaseUser.email
            //set to text view
            binding.profileEmail.text = email
        }
        else{
            //user is null and not logged in
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }
}


