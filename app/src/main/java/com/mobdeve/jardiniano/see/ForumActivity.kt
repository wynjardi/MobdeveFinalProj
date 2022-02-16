package com.mobdeve.jardiniano.see

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.mobdeve.jardiniano.see.databinding.ActivityForumBinding
import com.mobdeve.jardiniano.see.model.Post

class ForumActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForumBinding
    private lateinit var database: DatabaseReference
    private lateinit var adapter: ForumPostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForumBinding.inflate(layoutInflater)
        database = Firebase.database.reference
        setContentView(binding.root)

        adapter = ForumPostAdapter(this)
        with(binding.postRecyclerView) {
            adapter = this@ForumActivity.adapter
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false)
        }

        binding.newPostButton.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_post_add)

            val submitButton = dialog.findViewById<Button>(R.id.submit_button)
            val cancelButton = dialog.findViewById<Button>(R.id.cancel_button)

            submitButton.setOnClickListener {
                submitButton.isEnabled = false
                cancelButton.isEnabled = false

                val title = dialog.findViewById<TextInputEditText>(
                    R.id.title_input).text.toString()
                val desc = dialog.findViewById<TextInputEditText>(
                    R.id.desc_input).text.toString()
                Firebase.auth.currentUser?.let { user ->
                    val post = Post(
                        uid = user.uid,
                        title = title,
                        description = desc)
                    database.child("Posts") .push()
                        .setValue(post).addOnSuccessListener {
                            dialog.dismiss()
                        }
                }
            }

            cancelButton.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val posts = arrayListOf<Post>()
                for (data in dataSnapshot.children) {
                    val post = data.getValue<Post>()
                    if (post != null) {
                        post.id = data.key
                        posts.add(post)
                    }
                }
                Log.i("FORUM", posts.toString())
                adapter.items = posts
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("FORUM", "loadPost:onCancelled")
            }
        }

        database.child("Posts")
            .addValueEventListener(postListener)

        NavBar(findViewById(R.id.bottom_nav), this, R.id.chatIcon)
    }
}