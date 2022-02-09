package com.mobdeve.jardiniano.see

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

import com.mobdeve.jardiniano.see.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), RecyclerAdapterConcertList.OnItemClickListen {
    private var binding: ActivityMainBinding? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    var concertlst: ArrayList<Concerts?> = ArrayList<Concerts?>()
    var adapterrecycle: RecyclerAdapterConcertList? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)


        layoutManager = LinearLayoutManager(this)

        adapterrecycle = RecyclerAdapterConcertList(concertlst, this)
        binding!!.concertList.adapter = adapterrecycle
        binding!!.concertList.layoutManager = LinearLayoutManager(
            applicationContext,
            LinearLayoutManager.VERTICAL, false
        )

        NavBar(findViewById<BottomNavigationView>(R.id.bottom_nav), this, R.id.menuHomeIcon)
    }




        //Function for the view button click to go to Detailed Activity
        override fun onLoadClick(position: Int) {
            var goToDetailsHarryActivity =
                Intent(applicationContext, ConcertDetailsHarryActivity::class.java)

            startActivity(goToDetailsHarryActivity)
        }


//        fun onItemClick(item: Concerts, position: Int){
//
//            val intent = Intent(this@MainActivity,ConcertDetailsHarryActivity::class.java)
//            intent.putExtra("Concert Picture", item.ConcertPicture.toString())
//            startActivity(intent)
//        }


    }




//    override fun onCreateOptionsMenu(Menu search_menu){
//
//    }
