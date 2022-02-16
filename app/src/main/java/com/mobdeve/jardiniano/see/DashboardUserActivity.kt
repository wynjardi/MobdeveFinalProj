package com.mobdeve.jardiniano.see

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.mobdeve.jardiniano.see.databinding.ActivityDashboardUserBinding

@SuppressLint("NotifyDataSetChanged")
class DashboardUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardUserBinding
    private lateinit var database: DatabaseReference
    private lateinit var categoryAdapter: UserCategoryAdapter
    private lateinit var concertAdapter: UserConcertAdapter
    private var concertItems = arrayListOf<ModelConcert>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardUserBinding.inflate(layoutInflater)
        database = Firebase.database.reference
        setContentView(binding.root)

        categoryAdapter = UserCategoryAdapter(this)
        with(binding.categoryRecyclerView) {
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }

        val categoryListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val categories = arrayListOf<ModelCategory>()
                for (data in snapshot.children) {
                    val category = data.getValue<ModelCategory>()
                    if (category != null)
                        categories.add(category)
                }
                categoryAdapter.items = categories
                categoryAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("DASHBOARD", "loadCategory:onCancelled")
            }
        }
        database.child("Categories")
            .addValueEventListener(categoryListener)

        concertAdapter = UserConcertAdapter(this)
        with(binding.concertRecyclerView) {
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false)
            adapter = concertAdapter
        }

        val concertListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children) {
                    val concert = data.getValue<ModelConcert>()
                    if (concert != null)
                        concertItems.add(concert)
                }
                concertAdapter.items = concertItems
                concertAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("DASHBOARD", "loadCategory:onCancelled")
            }
        }
        database.child("Concerts")
            .addValueEventListener(concertListener)

        NavBar(findViewById<BottomNavigationView>(R.id.bottom_nav), this, R.id.menuHomeIcon)
    }


    fun filterByCategory(id: String) {
        concertAdapter.items = concertItems.filter {
            it.categoryId == id
        }
        concertAdapter.notifyDataSetChanged()
    }
}