package com.mobdeve.jardiniano.see

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private lateinit var et_remail: EditText
    private lateinit var et_rusername: EditText
    private lateinit var et_rpw: EditText
    private lateinit var btnRegister: Button
    private lateinit var textLogin: TextView
    private lateinit var progressRegister: ProgressBar

    // Firebase
    private lateinit var mAuth: FirebaseAuth
    private lateinit var reference: DatabaseReference
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        this.initComponents()
        this.initFirebase()
    }

    private fun initFirebase() {
        this.mAuth = FirebaseAuth.getInstance()
        this.database = FirebaseDatabase.getInstance()
        this.reference = FirebaseDatabase.getInstance().getReference(Keys.USERS.name)
    }

    private fun initComponents() {
        this.et_remail = findViewById(R.id.et_remail)
        this.et_rusername = findViewById(R.id.et_rusername)
        this.et_rpw = findViewById(R.id.et_rpw)
        this.btnRegister = findViewById(R.id.btn_register)
        this.textLogin = findViewById(R.id.tv_register_login)
        this.progressRegister = findViewById(R.id.pb_register)

        this.btnRegister.setOnClickListener {
            var email: String = et_remail.text.toString().trim()
            var name: String = et_rusername.text.toString().trim()
            var password: String = et_rpw.text.toString().trim()

            if(!checkEmpty(email, name, password)) {
                // add new user to db
                var user = User(email, name, password);
                storeUser(user)
            }
        }

        this.textLogin.setOnClickListener {
            val loginIntent = Intent(this, MainActivity::class.java)
            startActivity(loginIntent)
            finish()
        }
    }

    // checks if there are fields that are empty. returns true if there is an empty field and false if none
    private fun checkEmpty(email: String, name: String, password: String): Boolean {
        var hasEmpty: Boolean = false;

        // if email field is empty, prompt user to input email
        if(email.isEmpty()) {
            this.et_remail.error = "Required field"
            this.et_remail.requestFocus()
            hasEmpty = true
        }

        // if display name is empty, prompt user to input name
        if(name.isEmpty()) {
            this.et_rusername.error = "Required field"
            this.et_rusername.requestFocus()
            hasEmpty = true
        }

        // if password is empty, prompt user to input password
        if(password.isEmpty()) {
            this.et_rpw.error = "Required field"
            this.et_rpw.requestFocus()
            hasEmpty = true
        }

        // if email is invalid, prompt user to input a valid email
        if(!isValidEmail(et_remail.text.trim().toString())) {
            this.et_remail.error = "Please enter a valid e-mail address!"
            this.et_rusername.requestFocus()
            hasEmpty = true
        }

        // if password is less than 8 characters long, prompt user to input valid password
        if(password.length < 8) {
            this.et_rpw.error = "Password must be at least 8 characters!"
            this.et_rusername.requestFocus()
            hasEmpty = true
        }

        return hasEmpty
    }

    // checks input email and returns true if email is valid and false if not
    private fun isValidEmail(str: String): Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(str).matches()
    }

    // adds user to the database
    private fun storeUser(user: User) {
        progressRegister.visibility = View.VISIBLE

        // Register the user to Firebase
        mAuth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    database.getReference(Keys.USERS.name)
                        .child(mAuth.currentUser!!.uid)
                        .setValue(user).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                successfulRegistration()
                            } else {
                                failedRegistration()
                            }
                        }
                } else {
                    failedRegistration()
                }
            }
    }

    // notifies the user that they have successfully registered and redirect them to their main concert list
    private fun successfulRegistration() {
        this.progressRegister.visibility = View.GONE
        Toast.makeText(this, "You have successfully registered! Welcome!", Toast.LENGTH_SHORT).show();
        val chatIntent = Intent(this, MainActivity::class.java)
        startActivity(chatIntent)
        finish()
    }

    // presents an error message when user fails to register
    private fun failedRegistration() {
        this.progressRegister.visibility = View.GONE
        Toast.makeText(this, "You have failed to register :( try again!", Toast.LENGTH_SHORT).show()
    }
}