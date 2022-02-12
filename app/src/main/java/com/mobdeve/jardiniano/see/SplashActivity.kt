package com.mobdeve.jardiniano.see

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SplashActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        firebaseAuth = FirebaseAuth.getInstance()

        Handler().postDelayed(Runnable{
           startActivity(Intent(this, HomeActivity::class.java))
            checkUser()

        }, 1000)
    }

    private fun checkUser(){
        val firebaseUser= firebaseAuth.currentUser
//        if (firebaseUser == null){
//            startActivity(Intent(this, HomeActivity::class.java))
//            finish()
//        }
        if (firebaseUser !=null){

            val ref = FirebaseDatabase.getInstance().getReference("Users")
            ref.child(firebaseUser.uid)
                .addListenerForSingleValueEvent(object: ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot){

                        val userType = snapshot.child("userType").value
                        if (userType == "user"){
                            startActivity(Intent(this@SplashActivity, DashboardUserActivity::class.java))
                            finish()
                        }
                        else if (userType == "admin"){
                            startActivity(Intent(this@SplashActivity, DashboardAdminActivity::class.java))
                            finish()
                        }


                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
        }
    }
}
