package com.mobdeve.jardiniano.see

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.google.android.material.internal.TextWatcherAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mobdeve.jardiniano.see.databinding.ActivityConcertListAdminBinding

class ConcertListAdminActivity : AppCompatActivity() {

    private lateinit var binding : ActivityConcertListAdminBinding

    private companion object{
        const val TAG = "CONCERT_LIST_ADMIN_TAG"
    }
    //categ id and title
    private var categoryId = ""
    private var category = ""

    //arraylist to hold concerts
    private lateinit var concertArrayList: ArrayList<ModelConcert>

    //adapter
    private lateinit var adapterConcertAdmin: AdapterConcertAdmin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConcertListAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        categoryId = intent.getStringExtra("categoryId")!!
        category = intent.getStringExtra("category")!!

        //set concert categ
        binding.subTitleTv.text= category

        //load concerts
        loadConcertList()

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        //search
        binding.searchEt.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //filter data
                try{
                    adapterConcertAdmin.filter!!.filter(s)
                }
                catch (e: Exception){
                    Log.d(TAG, "onTextChanged: ${e.message}")
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }


        })
    }

    private fun loadConcertList(){
        //init
        concertArrayList = ArrayList()

        val ref = FirebaseDatabase.getInstance().getReference("Concerts")
        ref.orderByChild("categoryId").equalTo(categoryId)
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    //clear list
                    concertArrayList.clear()
                    for (ds in snapshot.children){
                        val model = ds.getValue(ModelConcert::class.java)

                        if (model != null) {
                            concertArrayList.add(model)
                            Log.d(TAG, "onDAtaChange: ${model.concertName} ${model.categoryId}")
                        }
                    }
                    adapterConcertAdmin = AdapterConcertAdmin(this@ConcertListAdminActivity, concertArrayList)
                    binding.concertsRv.adapter = adapterConcertAdmin
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
}