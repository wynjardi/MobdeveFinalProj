package com.mobdeve.jardiniano.see

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log

import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.mobdeve.jardiniano.see.databinding.ActivityConcertAddBinding
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class ConcertAddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConcertAddBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    //hold book categories
    private lateinit var categoryArrayList: ArrayList<ModelCategory>

    private var storageReference: StorageReference? = null

    private val PICK_IMAGE_REQUEST = 71
    //hold the filepath of image
    private var imageUri: Uri? = null

    private val TAG = "CONCERT_ADD_TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConcertAddBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //init firebase auth
        storageReference = FirebaseStorage.getInstance().reference
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

        binding.chooseImgBtn.setOnClickListener { concertImagePickIntent() }

        binding.submitBtn.setOnClickListener {
            validateData()
        }




    }


    private var concertname = ""
    private var  concertartist = ""
    private var category= ""


    private fun validateData(){
        //get data for name artist and categ
        concertname = binding.concertTitle.text.toString().trim()
        concertartist= binding.concertArtistName.text.toString().trim()
        category = binding.categoryTv.text.toString().trim()

        if (concertname.isEmpty()){
            Toast.makeText(this, "Enter Concert Name", Toast.LENGTH_SHORT).show()

        }
        else if(concertartist.isEmpty()){
            Toast.makeText(this, "Enter Concert Artist Name", Toast.LENGTH_SHORT).show()
        }
        else if(category.isEmpty()){
            Toast.makeText(this, "Pick Category", Toast.LENGTH_SHORT).show()
        } else{
        uploadImageToStorage()}
    }

//    private fun uploadPicToStorage(){
//
//
//        val timestamp = System.currentTimeMillis()
//        //path of picture in firebase storage
//        val filePathAndName= "Concerts/$timestamp"
//
//        //storage ref
//        val storageReference = FirebaseStorage.getInstance().getReference(filePathAndName)
//        storageReference.putFile(filePathAndName)
//    }

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
                    Log.d(TAG, "onDataChange: ${model.category}")
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
                selectedCategoryId = categoryArrayList[which].id
                selectedCategoryTitle = categoryArrayList[which].category

                //set categ to be shown
                binding.categoryTv.text= selectedCategoryTitle

                Log.d(TAG, "Selected Categ Id: $selectedCategoryId")
                Log.d(TAG, "Selected Categ Title: $selectedCategoryTitle")


            }
            .show()

    }

    private fun concertImagePickIntent(){

        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Concert Tour Image"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK){
            if (data == null || data.data == null){
                return
            }

            imageUri = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)

            } catch (e: IOException){
                e.printStackTrace()
            }
        }
    }

    private fun uploadImageToStorage(){
        
            val filePathAndName = ("Uploads/" )
            val storageReference = FirebaseStorage.getInstance().getReference(filePathAndName)

            val timestamp = System.currentTimeMillis()
            storageReference.putFile(imageUri!!)

                .addOnSuccessListener {taskSnapshot->
            val uriTask: Task<Uri> = taskSnapshot.storage.downloadUrl
            while (!uriTask.isSuccessful);
                    val uploadedImg = "${uriTask.result}"
                    uploadImgToDb(uploadedImg, timestamp)
                    }
                
                .addOnFailureListener{
                    Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
            }
        
}

    private fun uploadImgToDb(uploadedImg: String, timestamp: Long) {

        val uid = firebaseAuth.uid

        val data : HashMap<String, Any> = HashMap()
        data["uid"] = "$uid"
        data["id"] = "$timestamp"
        data["imageUrl"] = "$uploadedImg"
        data["concert artist"] = "$concertartist"
        data["concert name"] = "$concertname"
        data["category id"] = "$selectedCategoryId"
        data["timestamp"] = timestamp

        val ref = FirebaseDatabase.getInstance().getReference("Concerts")
        //add firebase db to CAtegories > categoryID > categ info
        ref.child("$timestamp")
            .setValue(data)
            .addOnSuccessListener {

                Toast.makeText(this, "Saved to Database", Toast.LENGTH_LONG).show()
                imageUri = null
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error saving to DB", Toast.LENGTH_LONG).show()
            }
    }


//    private fun addUploadRecordToDb(uri: String){
//
//
//
//    }

//    val concertAcitivityResultLauncher = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult(),
//        ActivityResultCallback<ActivityResult> { result ->
//            if (result.resultCode == RESULT_OK){
//                Log.d(TAG, "Concert Picked")
//                //pdfUri = result.data!!.data
//            }
//            else{
//                Log.d(TAG, "Pick cancelled")
//            }
//        }




}