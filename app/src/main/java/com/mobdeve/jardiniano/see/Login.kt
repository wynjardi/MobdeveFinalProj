package com.mobdeve.jardiniano.see

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class Login : AppCompatActivity() {
    var btn_lregister: Button? = null
    var btn_llogin: Button? = null
    var et_lusername: EditText? = null
    var et_lpassword: EditText? = null
    var databaseHelper: DatabaseHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        databaseHelper = DatabaseHelper(this)
        et_lusername = findViewById<View>(R.id.et_lusername) as EditText
        et_lpassword = findViewById<View>(R.id.et_lpassword) as EditText
        btn_llogin = findViewById<View>(R.id.btn_llogin) as Button
        btn_lregister = findViewById<View>(R.id.btn_lregister) as Button
        btn_lregister!!.setOnClickListener {
            val intent = Intent(this@Login, MainActivity::class.java)
            startActivity(intent)
        }
        btn_llogin!!.setOnClickListener {
            val username = et_lusername!!.text.toString()
            val password = et_lpassword!!.text.toString()
            val checklogin: Boolean = databaseHelper.CheckLogin(username, password)
            if (checklogin == true) {
                Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Invalid username or password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}