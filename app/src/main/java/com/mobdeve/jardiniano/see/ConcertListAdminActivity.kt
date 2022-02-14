package com.mobdeve.jardiniano.see

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ConcertListAdminActivity : AppCompatActivity() {

    //categ id and title
    private var categoryId = ""
    private var category = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_concert_list_admin)

        val intent = intent
        categoryId = intent.getStringExtra("categoryId")!!
        category = intent.getStringExtra("category")!!
    }
}