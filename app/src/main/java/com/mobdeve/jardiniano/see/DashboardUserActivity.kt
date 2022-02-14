package com.mobdeve.jardiniano.see

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.mobdeve.jardiniano.see.databinding.ActivityDashboardUserBinding


class DashboardUserActivity : AppCompatActivity(){


    private lateinit var binding: ActivityDashboardUserBinding

    //firebase
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var categoryArrayList: ArrayList<ModelCategory>
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        setupWithViewPagerAdapter(binding.viewPager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        binding.logoutBtn.setOnClickListener{
            firebaseAuth.signOut()

     //to change kung saan ma redirect
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }

    private fun setupWithViewPagerAdapter(viewPager: ViewPager){
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager,
        FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
        this
        )

        //init list
        categoryArrayList = ArrayList()

        //load categories from db
        val ref = FirebaseDatabase.getInstance().getReference("Categories")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{

            //setup adapter to view pager
            viewPager.adapter = viewPagerAdapter

            overrid e fun onDataChange(snapshot: DataSnapshot) {
                //clear list
                categoryArrayList.clear()

                //add data to models
                val modelALL = ModelCategory("01", "ALL", 1)

                //add to list
                categoryArrayList.add(modelALL)
                viewPagerAdapter.addFragment(
                    ConcertUserFragment.newInstance()
                )

                //refresh list
                viewPagerAdapter.notifyDataSetChanged()

                //load from firebase db
                for (ds in snapshot.children){
                    //get data in model
                    val model = ds.getValue(ModelCategory::class.java)
                    //add to list
                    categoryArrayList.add(model!!)
                    //add to viewPagerAdapter
                    viewPagerAdapter.addFragment(
                            ConcertUserFragment.newInstance(
                                "${model.id}",
                                "${model.category}"
                            ), model.category
                        )

                    //refresh list
                    viewPagerAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    class ViewPagerAdapter(fm: FragmentManager, behavior: Int, context: Context): FragmentPagerAdapter(fm, behavior){

        //holds list of fragments for new instances of some fragment in each category
        private val fragmentlist: ArrayList<ConcertUserFragment> = ArrayList()
        //list of titles of categories
        private val fragmentTitleList: ArrayList<String> = ArrayList()
        private val context:Context

        init {
            this.context = context
        }

        override fun getCount(): Int {
            return fragmentlist.size
        }

        override fun getItem(position: Int): Fragment {
            return fragmentlist[position]
        }

        public fun addFragment(fragment: ConcertUserFragment, title: String){
            //add fragment and add title that will be passes as a parameter in fragmentlist
            fragmentlist.add(fragment)
            fragmentlist.add(title)
        }


    }

    private fun checkUser(){
        //get current user
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null){
            val email = firebaseUser.email
            //set to textview of toolbar
            binding.subTitleTv.text = email
        }


    }
}