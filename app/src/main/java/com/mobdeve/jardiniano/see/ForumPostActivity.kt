package com.mobdeve.jardiniano.see

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.mobdeve.jardiniano.see.databinding.ActivityForumPostBinding
import com.mobdeve.jardiniano.see.model.Comment
import com.mobdeve.jardiniano.see.model.Post


class ForumPostActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityForumPostBinding
    private lateinit var adapter: ForumCommentAdapter
    private lateinit var post: Post

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForumPostBinding.inflate(layoutInflater)
        database = Firebase.database.reference
        setContentView(binding.root)

        post = (intent.getSerializableExtra("post") as? Post)!!

        binding.titleText.text = post.title
        binding.subtitleText.text = post.description

        post.uid?.let { uid ->
            database.child("Users")
                .child(uid).child("name")
                .get().addOnSuccessListener {
                    val name = it.getValue<String>()
                    binding.authorText.text = name
                }
        }

        adapter = ForumCommentAdapter()
        with(binding.commentRecyclerView) {
            adapter = this@ForumPostActivity.adapter
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false)
        }

        val commentListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val comments = arrayListOf<Comment>()
                for (data in dataSnapshot.children) {
                    val comment = data.getValue<Comment>()
                    if (comment != null && comment.postId == post.id) {
                        comments.add(comment)
                    }
                }
                Log.i("FORUM", comments.toString())
                adapter.items = comments
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("FORUM", "loadPost:onCancelled")
            }
        }

        database.child("Comments")
            .addValueEventListener(commentListener)

        binding.button.setOnClickListener {
            binding.button.isEnabled = false

            val content = binding.commentInput.text.toString()
            val comment = Comment(
                uid = Firebase.auth.currentUser?.uid,
                postId = post.id,
                content = content
            )

            database.child("Comments").push()
                .setValue(comment).addOnSuccessListener {
                    binding.button.isEnabled = true
                    binding.commentInput.setText("")
                }


        }

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

    }
}