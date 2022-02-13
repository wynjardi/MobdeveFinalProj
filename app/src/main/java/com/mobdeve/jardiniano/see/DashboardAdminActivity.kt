package com.mobdeve.jardiniano.see

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.google.firebase.auth.FirebaseAuth
import com.mobdeve.jardiniano.see.databinding.ActivityDashboardAdminBinding


class DashboardAdminActivity : AppCompatActivity(){


    private lateinit var binding: ActivityDashboardAdminBinding

    //firebase
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()


        //handle click logout
        binding.logoutbtn.setOnClickListener{
            firebaseAuth.signOut()
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        //handle for add category
        binding.addCategBtn.setOnClickListener {
            startActivity(Intent(this,CategoryAddActivity::class.java))
        }
    }

    private fun checkUser(){
        //get user
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null){
            val email = firebaseUser.email
            binding.adminTitleTv.text=email
        }


    }
}