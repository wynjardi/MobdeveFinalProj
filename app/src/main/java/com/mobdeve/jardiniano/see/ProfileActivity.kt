package com.mobdeve.jardiniano.see

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
        loadUserInfo()

        //handle click, opens edit profile
        binding.profileEditBtn.setOnClickListener{
            startActivity(Intent(this, EditProfile::class.java))
            finish()

        }


        NavBar(findViewById<BottomNavigationView>(R.id.bottom_nav), this, R.id.profileIcon)
    }

    private fun loadUserInfo() {
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseAuth.uid!!)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    //gets user info
                    val email = "${snapshot.child("email").value}"
                    val uid = "${snapshot.child("uid").value}"
                    val name = "${snapshot.child("name").value}"

                    //set data
                    binding.emaiLTv.text = email
                    binding.nameTv.text = name
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

}


