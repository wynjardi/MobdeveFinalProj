package com.mobdeve.jardiniano.see

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UserConcertAdapter(private val context: Context):
    RecyclerView.Adapter<UserConcertAdapter.ViewHolder>() {

    var items: List<ModelConcert> = listOf()

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val card: CardView = view.findViewById(R.id.card_view)
        val image: ImageView = view.findViewById(R.id.concert_image)
        val name: TextView = view.findViewById(R.id.concert_name_text)
        val artist: TextView = view.findViewById(R.id.concert_artist_text)
        val date: TextView = view.findViewById(R.id.concert_date_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_concert, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        Glide.with(holder.image)
            .load(item.imageUrl)
            .centerCrop()
            .into(holder.image)

        holder.name.text = item.concertName
        holder.artist.text = item.concertArtist
        holder.date.text = MyApplication
            .formatTimeStamp(item.timestamp)

        holder.card.setOnClickListener {
            val intent = Intent(context, ConcertDetailActivity::class.java)
            intent.putExtra("concertId", item.id)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = items.count()
}