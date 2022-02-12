package com.mobdeve.jardiniano.see

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.google.firebase.auth.FirebaseAuth

import com.mobdeve.jardiniano.see.databinding.ActivityDashboardUserBinding


class DashboardUserActivity : AppCompatActivity(){


    private lateinit var binding: ActivityDashboardUserBinding

    //firebase
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.logoutbtn.setOnClickListener{
            firebaseAuth.signOut()
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }


    }

    private fun checkUser(){
        //get user
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null){
            val email = firebaseUser.email
            binding.TitleTv.text=email
        }


    }
}