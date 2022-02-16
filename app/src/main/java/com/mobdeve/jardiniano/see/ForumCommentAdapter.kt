package com.mobdeve.jardiniano.see

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.mobdeve.jardiniano.see.model.Comment


class ForumCommentAdapter: RecyclerView.Adapter<ForumCommentAdapter.ViewHolder>() {

    var items: List<Comment> = listOf()

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val avatar: TextView = view.findViewById(R.id.avatar)
        val author: TextView = view.findViewById(R.id.author_text)
        val comment: TextView = view.findViewById(R.id.comment_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder( LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comment, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.comment.text = item.content
        item.uid?.let { uid ->
            Firebase.database.reference
                .child("Users")
                .child(uid)
                .child("name")
                .get().addOnSuccessListener {
                    val name = it.getValue<String>()
                    if (name != null) {
                        holder.avatar.text = name.first().uppercaseChar().toString()
                        holder.author.text = name
                    }
                }
        }

    }

    override fun getItemCount(): Int = items.count()

}