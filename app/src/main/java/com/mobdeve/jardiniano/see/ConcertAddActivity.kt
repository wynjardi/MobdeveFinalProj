package com.mobdeve.jardiniano.see

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mobdeve.jardiniano.see.databinding.ActivityConcertAddBinding

class ConcertAddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConcertAddBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    //hold book categories
    private lateinit var categoryArrayList: ArrayList<ModelCategory>

    private val TAG = "CONCERT_ADD_TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConcertAddBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        loadConcertCategories()

        //setup progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.categoryTv.setOnClickListener {
            categoryPickDialog()
        }

        binding.submitBtn.setOnClickListener {
            validateData()
        }


    }

    private var concertName = ""
    private var  concertArtist = ""
    private var category= ""


    private fun validateData(){
            //get data for name artist and categ
        concertName = binding.concertTitleFill.toString().trim()
        concertArtist= binding.concertArtistNameFill.toString().trim()
        category = binding.categoryTv.text.toString().trim()

        if (concertName.isEmpty()){
            Toast.makeText(this, "Enter Concert Name", Toast.LENGTH_SHORT).show()

        }
        else if(concertArtist.isEmpty()){
            Toast.makeText(this, "Enter Concert Artist Name", Toast.LENGTH_SHORT).show()
        }
        else if(category.isEmpty()){
            Toast.makeText(this, "Pick Category", Toast.LENGTH_SHORT).show()
        } //uploadChuchuToStorage()
    }

    //private fun uploadChuchuToStorage(){}

    private fun loadConcertCategories() {

        //init arraylist
        categoryArrayList = ArrayList()

        //db ref to load categories
        val ref = FirebaseDatabase.getInstance().getReference("Categories")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                categoryArrayList.clear()
                for (ds in snapshot.children){
                    //get data
                    val model= ds.getValue(ModelCategory::class.java)

                    //add to arraylist
                    categoryArrayList.add(model!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }

    private var selectedCategoryId = ""
    private var selectedCategoryTitle = ""
    private fun categoryPickDialog(){
        Log.d(TAG, "categoryPickDialog: Showing categories")

        //get string array of categories from arraylist
        val categoriesArray = arrayOfNulls<String>(categoryArrayList.size)
        for (i in categoryArrayList.indices){
            categoriesArray[i]= categoryArrayList[i].category
        }

        //alert
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pick Category")
            .setItems(categoriesArray){dialog, which ->
                //handle item click
                //get clicked
                selectedCategoryId = categoryArrayList[which].category
                selectedCategoryTitle = categoryArrayList[which].id

                //set categ to be shown
                binding.categoryTv.text= selectedCategoryTitle

                Log.d(TAG, "Selected Categ Id: $selectedCategoryId")
                Log.d(TAG, "Selected Categ Id: $selectedCategoryId")


            }
            .show()

    }

    private fun concertPickIntent(){

        val intent = Intent()
        intent.type = "application/concert"
        intent.action = Intent.ACTION_GET_CONTENT
        concertAcitivityResultLauncher.launch(intent)
    }

    val concertAcitivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult> { result ->
            if (result.resultCode == RESULT_OK){
                Log.d(TAG, "Concert Picked")
                //pdfUri = result.data!!.data
            }
            else{
                Log.d(TAG, "Pick cancelled")
            }
        }
    )



}