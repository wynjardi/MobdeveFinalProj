package com.mobdeve.jardiniano.see

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mobdeve.jardiniano.see.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivityRegisterBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    // Firebase
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""
    private var name = ""
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure ActionBar // enable back button
        actionBar = supportActionBar!!
        actionBar.title = "Register"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Signing in...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //handle click, begin regis
        binding.btnRegister.setOnClickListener{
            //validate data
            validateData()
        }

        //handle click, open login instead
        binding.logreg.setOnClickListener{
            startActivity(Intent(this, Login::class.java))
            finish()
        }

    }

    private fun validateData() {
        //get data
        email = binding.etRemail.text.toString().trim()
        password = binding.etRpassword.text.toString().trim()
        name = binding.etLname.text.toString().trim()

        //validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
            binding.etRemail.error="Invalid: Use the correct email format!"
        }
        else if (TextUtils.isEmpty(name)) {
            //name not entered
            binding.etRpassword.error = "Required Field: Please enter your name!"
        }
        else if (TextUtils.isEmpty(password)){
            //password not entered
            binding.etRpassword.error="Required Field: Please enter a password!"
        }
        else if (password.length<6){
            //password length is less than 6
            binding.etRpassword.error = "Invalid: Password needs more than 5 characters"
        }
        else{
            //data is valid, continue reg process
            firebaseRegister()
        }

    }

    private fun firebaseRegister() {
        //show progress
        progressDialog.show()

        //create account
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                // added this, add user info
                updateUserInfo()
                //reg success
                progressDialog.dismiss()
                //get current user
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this,"You successfully registered with $email! Welcome to InStaged!",Toast.LENGTH_SHORT).show()

                //once registered, opens concert list view
                startActivity(Intent(this, DashboardUserActivity::class.java))
                finish()
            }

            .addOnFailureListener{e->
                //reg failed
                progressDialog.dismiss()
                Toast.makeText(this, "Register failed due to ${e.message}",Toast.LENGTH_SHORT).show()

            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // go back to prev activity when action bar back btn is closed
        return super.onSupportNavigateUp()
    }

    private fun updateUserInfo(){
        progressDialog.setMessage("Saving user info...")

        // we can get it now bc user is registered
        val uid = firebaseAuth.uid
        val hashMap: HashMap<String, Any?> = HashMap()
        hashMap["uid"] = uid
        hashMap["email"] = email
        hashMap["password"] = password
        hashMap["userType"] = "user"


        //set data to db
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(uid!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this,"Account created..", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@RegisterActivity, DashboardUserActivity::class.java))
                finish()
            }
            .addOnFailureListener{e->
                progressDialog.dismiss()
                Toast.makeText(this,"Failed saving user info because of ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}