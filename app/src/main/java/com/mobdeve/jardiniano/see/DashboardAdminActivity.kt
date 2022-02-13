package com.mobdeve.jardiniano.see

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mobdeve.jardiniano.see.databinding.ActivityDashboardAdminBinding


class DashboardAdminActivity : AppCompatActivity(){


    private lateinit var binding: ActivityDashboardAdminBinding

    //firebase
    private lateinit var firebaseAuth: FirebaseAuth


    //araylist to hold categ
    private lateinit var categoryArrayList: ArrayList<ModelCategory>

    //adapter
    private lateinit var adapterCategory: AdapterCategory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
        loadCategories()


        //search
        binding.searchEt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //called as and when user is typing
                try{
                    adapterCategory.filter.filter(s)
                }
                catch(e:Exception){}
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        //handle click logout
        binding.logoutbtn.setOnClickListener{
            firebaseAuth.signOut()
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        //handle for add category
        binding.addCategBtn.setOnClickListener {
            startActivity(Intent(this,CategoryAddActivity::class.java))
        }

        //start add concert view page (to change)
        binding.addConcertDet.setOnClickListener{
            startActivity(Intent(this, ConcertAddActivity::class.java))
        }

    }

    private fun loadCategories() {
        //init arraylist
        categoryArrayList = ArrayList()


        val ref = FirebaseDatabase.getInstance().getReference("Categories")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear list before adding data
                categoryArrayList.clear()
                for (ds in snapshot.children){
                    //get data as model
                    val model = ds.getValue(ModelCategory::class.java)

                    //add to arraylist
                    categoryArrayList.add(model!!)


                }
                    //setup adapter
                adapterCategory = AdapterCategory(this@DashboardAdminActivity, categoryArrayList)
                //set adapter to recycler
                binding.categoriesRc.adapter = adapterCategory
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun checkUser(){
        //get user
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null){
            val email = firebaseUser.email
            binding.adminTitleTv.text=email
        }


    }
}