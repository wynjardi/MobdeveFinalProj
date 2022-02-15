package com.mobdeve.jardiniano.see

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.mobdeve.jardiniano.see.databinding.ActivityEditProfileBinding
import com.mobdeve.jardiniano.see.databinding.RowConcertSubscribeBinding

class EditProfile : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityEditProfileBinding

    //firebase
    private lateinit var firebaseAuth: FirebaseAuth

    //progress dialog
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setup progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

    //init firebase
        firebaseAuth = FirebaseAuth.getInstance()
        loadUserInfo()

        //handle click, update profile
        binding.updateBtn.setOnClickListener{
        validatedata()
    }

    }

    private var name = ""
    private fun validatedata() {
        //get data
        name = binding.nameEt.text.toString().trim()

        //validate data
        if (name.isEmpty()){
            //name empty
            Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show()
        }
        else{
            //name is entered
            updateProfile("")
        }

    }

    private fun updateProfile(uploadedImageUrl: String) {
    progressDialog.setMessage("Updating profile...")

        val hashMap: HashMap<String, Any> = HashMap()
        hashMap["name"] = "$name"

        //update to db
        val reference = FirebaseDatabase.getInstance().getReference("Users")
        reference.child(firebaseAuth.uid!!)
            .updateChildren(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Profile updated!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{e->
                progressDialog.dismiss()
                Toast.makeText(this,"Failed to update profile", Toast.LENGTH_SHORT).show()
            }
    }


    private fun loadUserInfo() {
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseAuth.uid!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //gets user info
                    val email = "${snapshot.child("email").value}"
                    val uid = "${snapshot.child("uid").value}"
                    val name = "${snapshot.child("name").value}"

                    //set data
                    binding.nameEt.setText(name)
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

}