package com.mobdeve.jardiniano.see

import android.content.Context
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView

class NavBar(bottomNavigationView: BottomNavigationView, appCon: Context, navItem: Int) {
    init {

        bottomNavigationView.selectedItemId = navItem
        bottomNavigationView.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.menuHomeIcon -> {
                    val intent = Intent(appCon, MainActivity::class.java)
                    appCon.startActivity(intent)
                }
                R.id.profileIcon -> {
                    val intent = Intent(appCon, ProfileActivity::class.java)
                    appCon.startActivity(intent)
                }
                R.id.subscribeIcon -> {
                    val intent = Intent(appCon, SubscribelistActivity::class.java)
                    appCon.startActivity(intent)
                }
                R.id.chatIcon -> {
                    val intent = Intent(appCon, ForumActvitity::class.java)
                    appCon.startActivity(intent)
                }
            }
            true
        }
    }
}