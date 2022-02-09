package com.mobdeve.jardiniano.see

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

import com.mobdeve.jardiniano.see.databinding.ActivityMainBinding
import android.R
import android.content.Intent
import android.widget.ListView
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var concertlst: ArrayList<Concerts>
//    private var adapterrecycle: MyAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        layoutManager = LinearLayoutManager(this)

        val ConcertPicture = arrayOf(
            com.mobdeve.jardiniano.see.R.drawable.harry_styles_picture, com.mobdeve.jardiniano.see.R.drawable.billie_eilish_picture,
            com.mobdeve.jardiniano.see.R.drawable.taylor_swift_picture)

        val ConcertName = arrayOf("Love on Tour", "When We All Fall Asleep Tour", "Fearless Tour")

        val ConcertArtistName = arrayOf("Harry Styles", "Billie Eilish", "Taylor Swift")

        val ConcertDateTime = arrayOf("November 20, 2021 ", "July 06, 2021 ", "March 16, 2020 ")
        val ConcertPlace = arrayOf("Mall of Asia Arena, Pasay City", "Araneta Coliseum, Quezon City", "Kia Theatre, Quezon City")
        val ConcertPrices = arrayOf("Total prices are as follows: P20,000 P10,000 P5,000", "Total prices are as follows: P30,000 P15,000 P5,000", "Total prices are as follows: P20,000 P10,000 P5,000")

        concertlst = ArrayList()

        for(i in ConcertName.indices){
            val concerts = Concerts(ConcertPicture[i], ConcertName[i], ConcertArtistName[i],ConcertDateTime[i],ConcertPlace[i],ConcertPrices[i])
            concertlst.add(concerts)
        }


//
//        adapterrecycle = RecyclerAdapterConcertList(concertlst, this)
//        binding!!.concertList.adapter = adapterrecycle
//        binding!!.concertList.layoutManager = LinearLayoutManager(
//            applicationContext,
//            LinearLayoutManager.VERTICAL, false
//        )



        binding.listview.isClickable = true
        binding.listview.adapter = MyAdapter(this, concertlst)

        //when clicked , must go to specific detailed concert activity
        binding.listview.setOnItemClickListener { parent,view, position, id ->

           when (position){
               0-> startActivity(
                   Intent(
                       this@MainActivity,
                       ConcertDetailsHarryActivity::class.java))}
           when (position){
               1-> startActivity(
                   Intent(
                       this@MainActivity,
                       ConcertDetailsBillieActivity::class.java ))}
           when (position){
               2-> startActivity(Intent(
               this@MainActivity,
               ConcertDetailsTaylorActivity::class.java
           ))}

        }

        NavBar(findViewById<BottomNavigationView>(com.mobdeve.jardiniano.see.R.id.bottom_nav), this, com.mobdeve.jardiniano.see.R.id.menuHomeIcon)
        }

//        binding.listview.setOnItemClickListener { parent,view, position, id ->
//
//            val ConcertPicture = ConcertPicture[position]
//            val ConcertName = ConcertName[position]
//            val ConcertArtistName = ConcertArtistName[position]
//            val ConcertDateTime = ConcertDateTime[position]
//            val ConcertPlace = ConcertPlace[position]
//            val ConcertPrices = ConcertPrices[position]
//
//            val i = Intent(this,ConcertDetailsHarryActivity::class.java)
//            i.putExtra( "ConcertPicture",ConcertPicture)
//            i.putExtra( "ConcertName",ConcertName)
//            i.putExtra( "ConcertArtistName",ConcertArtistName)
//            i.putExtra( "ConcertDateTime",ConcertDateTime)
//            i.putExtra( "ConcertPlace",ConcertPlace)
//            i.putExtra( "ConcertPrices",ConcertPrices)
//            startActivity(i)
//        }
//        binding.listview.setOnClickListener{
//            startActivity(
//                Intent(
//                    this@MainActivity,
//                    ConcertDetailsHarryActivity::class.java
//                )
//            )
//
//        }



    }




        //Function for the view button click to go to Detailed Activity
//        override fun onLoadClick(position: Int) {
//            var goToDetailsHarryActivity =
//                Intent(applicationContext, ConcertDetailsHarryActivity::class.java)
//
//            startActivity(goToDetailsHarryActivity)
//        }


//        fun onItemClick(item: Concerts, position: Int){
//
//            val intent = Intent(this@MainActivity,ConcertDetailsHarryActivity::class.java)
//            intent.putExtra("Concert Picture", item.ConcertPicture.toString())
//            startActivity(intent)
//        }







//    override fun onCreateOptionsMenu(Menu search_menu){
//
//    }
