package com.mobdeve.jardiniano.see

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseAuth


class Login : AppCompatActivity() {
    private lateinit var et_lemail: EditText
    private lateinit var et_lpassword: EditText
    private lateinit var btn_llogin: Button
    private lateinit var tvCreate: TextView
    private lateinit var pbLogin: ProgressBar

    // Firebase and Permissions
    private lateinit var mAuth: FirebaseAuth

    val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
    val requestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        this.initFirebase()
        this.initComponents()

        // once accepted the login details, will redirect to our main list of concerts
        if (this.mAuth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun initFirebase() {
        this.mAuth = FirebaseAuth.getInstance();
    }

    private fun initComponents() {
        this.et_lemail = findViewById(R.id.et_lemail)
        this.et_lpassword = findViewById(R.id.et_lpassword)
        this.btn_llogin = findViewById(R.id.btn_llogin)
        this.tvCreate = findViewById(R.id.logreg)
        this.pbLogin = findViewById(R.id.pb_login)

        this.btn_llogin.setOnClickListener {
            // gets the user input
            var email = et_lemail.text.toString().trim()
            var password = et_lpassword.text.toString().trim()

            // if user details for pw and email are both filled in, will log in
            if(!checkEmpty(email, password)) {
                signIn(email, password)
            }
        }

    }

    // checks for empty fields
    // returns true if there is an empty field and false if none
    private fun checkEmpty(email: String, password: String): Boolean {
        var hasEmpty: Boolean = false;

        // if email field is empty, prompt user to input email
        if(email.isEmpty()) {
            this.et_lemail.error = "Required field"
            this.et_lemail.requestFocus()
            hasEmpty = true
        }

        // if password is empty, prompt user to input password
        if(password.isEmpty()) {
            this.et_lpassword.error = "Required field"
            this.et_lpassword.requestFocus()
            hasEmpty = true
        }

        return hasEmpty
    }

    // logs the user in to their account given an email and a password
    private fun signIn(email: String, password: String) {
        this.pbLogin.visibility = View.VISIBLE

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                // if successfully logged in, redirect to our main concert list
                if (task.isSuccessful) {
                    val chatIntent = Intent(this, MainActivity::class.java)
                    startActivity(chatIntent)
                    finish()
                } else {
                    failedLogin();
                }
            }
    }

    // presents an error message when user fails to log in
    private fun failedLogin() {
        this.pbLogin.visibility = View.GONE
        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
    }
}