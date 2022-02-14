package com.mobdeve.jardiniano.see

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mobdeve.jardiniano.see.databinding.ActivityConcertEditBinding

class ConcertEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConcertEditBinding

    private var concertId= ""

    private lateinit var progressDialog: ProgressDialog

    //arraylsit to hold categ titels
    private lateinit var categoryTitleArrayList : ArrayList<String>

    private lateinit var categoryIdArrayList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConcertEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        concertId = intent.getStringExtra("concertId")!!

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please WAit")
        progressDialog.setCanceledOnTouchOutside(false)

        loadCategories()
        loadConcertInfo()


        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.categoryTv.setOnClickListener {
            categoryDialog()

        }

        binding.submitBtn.setOnClickListener {
            validateData()
        }

    }

    private fun loadConcertInfo() {


        val ref = FirebaseDatabase.getInstance().getReference("Concerts")
        ref.child(concertId)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    selectedCategoryId = snapshot.child("categoryId").value.toString()
                    val concertArtist = snapshot.child("concertArtist").value.toString()
                    val concertName = snapshot.child("concertName").value.toString()

                    //set to views
                    binding.concertTitle.setText(concertName)
                    binding.concertArtistName.setText(concertArtist)

                    val refConcertCategory = FirebaseDatabase.getInstance().getReference("Categories")
                    refConcertCategory.child(selectedCategoryId)
                        .addListenerForSingleValueEvent(object: ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                //get categ
                                val category = snapshot.child("category").value
                                //set to textview
                                binding.categoryTv.text = category.toString()
                            }

                            override fun onCancelled(error: DatabaseError) {

                            }

                        })
                }

                override fun onCancelled(error: DatabaseError) {

                }


            })

    }

    private var concertName = ""
    private var concertArtist = ""
    private fun validateData() {
        concertName = binding.concertTitle.text.toString().trim()
        concertArtist = binding.concertArtistName.text.toString().trim()

        if (concertName.isEmpty()){
            Toast.makeText(this, "Enter Concert Name", Toast.LENGTH_SHORT).show()
        }
        else if (concertArtist.isEmpty()){
            Toast.makeText(this, "Enter Concert Artist", Toast.LENGTH_SHORT).show()
        }
        else if (selectedCategoryId.isEmpty()){
            Toast.makeText(this, "Pick Category", Toast.LENGTH_SHORT).show()
        }
        else {updateConcert()}
    }

    private fun updateConcert() {
        progressDialog.show()

        val hashMap = HashMap<String, Any>()
        hashMap["concertName"] = "$concertName"
        hashMap["concertArtist"] = "$concertArtist"
        hashMap["categoryId"] = "$selectedCategoryId"

        val ref = FirebaseDatabase.getInstance().getReference("Concerts")
        ref.child(concertId)
            .updateChildren(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {  e->
                progressDialog.dismiss()
                Toast.makeText(this, "Failed to update due to ${e.message}", Toast.LENGTH_SHORT).show()
            }

    }

    private var selectedCategoryId = ""
    private var selectedCategoryTitle = ""
    private fun categoryDialog() {

        val categoriesArray = arrayOfNulls<String>(categoryTitleArrayList.size)
        for(i in categoryTitleArrayList.indices){
            categoriesArray[i] = categoryTitleArrayList[i]

        }

        //alert
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose Category")
            .setItems(categoriesArray){dialog, position ->

                selectedCategoryId = categoryIdArrayList[position]
                selectedCategoryTitle = categoryTitleArrayList[position]

                //set to txtview
                binding.categoryTv.text = selectedCategoryTitle
            }
            .show()

    }

    private fun loadCategories() {
        categoryTitleArrayList = ArrayList()
        categoryIdArrayList = ArrayList()

        val ref = FirebaseDatabase.getInstance().getReference("Categories")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear list before adding data
                categoryIdArrayList.clear()
                categoryTitleArrayList.clear()
                for (ds in snapshot.children){
                    //get data as model
                    val id = "${ds.child("id").value}"
                    val category = "${ds.child("category").value}"


                    //add to arraylist
                    categoryIdArrayList.add(id)
                    categoryTitleArrayList.add(category)


                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }
}