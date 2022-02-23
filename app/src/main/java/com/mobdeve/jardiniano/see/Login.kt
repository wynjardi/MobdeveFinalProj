package com.mobdeve.jardiniano.see

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.mobdeve.jardiniano.see.databinding.ActivityLoginBinding
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class Login : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding:ActivityLoginBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //ProgressDialog
    private lateinit var progressDialog:ProgressDialog

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""
    var callbackManager: CallbackManager?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure actionbar
        actionBar = supportActionBar!!
        actionBar.title="Login"

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Logging in...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        //fb login api

        callbackManager = CallbackManager.Factory.create()

        printKeyHash()

        binding!!.fbBtn.setReadPermissions("email")
        binding!!.fbBtn.setOnClickListener {
            signIn()
            checkUser()
        }



        //handle click, open reg
        binding.logreg.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        //handle click, begin login
        binding.btnLlogin.setOnClickListener{

            //before loggin in, validate data
            validateData()
        }
    }

    private fun signIn() {
        binding!!.fbBtn.registerCallback(callbackManager, object: FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {

                handleFacebookAccessToken(result!!.accessToken)

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
//                        startActivity(Intent(this@Login, DashboardUserActivity::class.java))
//                        finish()
                    }
                    .addOnFailureListener{e->

                    }


            }

            override fun onCancel() {

            }

            override fun onError(error: FacebookException?) {

            }

        })
    }

    private fun handleFacebookAccessToken(accessToken: AccessToken) {
        //get credential
        val credential = FacebookAuthProvider.getCredential(accessToken!!.token)
        firebaseAuth!!.signInWithCredential(credential)
            .addOnFailureListener{ e->
                Toast.makeText(this, e.message,Toast.LENGTH_SHORT).show()
            }
            .addOnSuccessListener { result ->
                val email = result.user!!.email
                val user = firebaseAuth.currentUser
                Toast.makeText(this, "You logged in with email: " + email,Toast.LENGTH_SHORT).show()

            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager!!.onActivityResult(requestCode,resultCode,data)
    }

    private fun printKeyHash() {
        try {
            val info = packageManager.getPackageInfo("com.mobdeve.jardiniano.see", PackageManager.GET_SIGNATURES)
            for (signature in info.signatures)
            {
                val md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray())
                Log.d("KEYHASH", Base64.encodeToString(md.digest(), Base64.DEFAULT))

            }


        }
        catch (e: PackageManager.NameNotFoundException){

        }
        catch (e: NoSuchAlgorithmException){

        }
    }




    private fun validateData(){
        //get data
        email = binding.etLemail.text.toString().trim()
        password = binding.etLpassword.text.toString().trim()

        //validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
            binding.etLemail.error="Invalid email format"
        }
        else if (TextUtils.isEmpty(password)){
            // no password entered
            binding.etLpassword.error = "Please enter password"
        }
        else{
            //data is validated, begin login
            firebaseLogin()
        }
    }

    private fun firebaseLogin(){
        //show progress
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //login success
//                progressDialog.dismiss()
//                //get user info
//                val firebaseUser = firebaseAuth.currentUser
//                val email = firebaseUser!!.email
//                Toast.makeText(this,"Logged in as $email", Toast.LENGTH_SHORT).show()
//
//                //open profile
//                startActivity(Intent(this, ProfileActivity::class.java))
//                finish()
                checkUser()
            }

            .addOnFailureListener{ e->
                //login failed
                progressDialog.dismiss()
                Toast.makeText(this, "Login failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {
        //if user is already logged in, redirect to profile activity
        val firebaseUser = firebaseAuth.currentUser!!

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseUser.uid)
            .addListenerForSingleValueEvent(object: ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot){
                    progressDialog.dismiss()
                    val userType = snapshot.child("userType").value
                    if (userType == "user"){
                        startActivity(Intent(this@Login, DashboardUserActivity::class.java))
                        finish()
                    }
                    else if (userType == "admin"){
                        startActivity(Intent(this@Login, DashboardAdminActivity::class.java))
                        finish()
                    }


                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
//        if (firebaseUser != null){
//            //user is logged in
//            startActivity(Intent(this, ProfileActivity::class.java))
//            finish()
//        }
    }

//    private fun loginUser(){
//
//        progressDialog.setMessage("Logging in")
//        progressDialog.show()
//
//        firebaseAuth.signInWithEmailAndPassword(email, password)
//            .addOnSuccessListener {
//
//                checkUser()
//            }
//
//            .addOnFailureListener { e->
//                Toast.makeText(this,"Logged in failed", Toast.LENGTH_SHORT).show()
//            }
//    }
}