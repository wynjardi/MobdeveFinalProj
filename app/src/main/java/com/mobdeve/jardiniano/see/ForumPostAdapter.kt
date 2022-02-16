package com.mobdeve.jardiniano.see

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.mobdeve.jardiniano.see.model.Post

class ForumPostAdapter(private val context: Context):
    RecyclerView.Adapter<ForumPostAdapter.ViewHolder>() {

    var items: List<Post> = listOf()

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val card: CardView = view.findViewById(R.id.card_view)
        val avatar: TextView = view.findViewById(R.id.avatar)
        val title: TextView = view.findViewById(R.id.title_text)
        val subtitle: TextView = view.findViewById(R.id.subtitle_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        item.uid?.let { uid ->
            Firebase.database.reference
                .child("Users")
                .child(uid)
                .child("name")
                .get().addOnSuccessListener {
                    val name = it.getValue<String>()
                    if (name != null) {
                        holder.avatar.text = name.first().uppercaseChar().toString()
                    }
                }
        }

        holder.title.text = item.title
        holder.subtitle.text = item.description

        holder.card.setOnClickListener {
            val intent = Intent(context, ForumPostActivity::class.java)
            intent.putExtra("post", item)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.count()

}