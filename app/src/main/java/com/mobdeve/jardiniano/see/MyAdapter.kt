package com.mobdeve.jardiniano.see

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MyAdapter(private val context : Activity, private val arrayList : ArrayList<Concerts>) : ArrayAdapter<Concerts>(context,
                R.layout.item_row_concert,arrayList) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.item_row_concert, null)

        val imageView : ImageView = view.findViewById(R.id.concert_img)
        val concertName : TextView = view.findViewById(R.id.concert_name)
        val concertArtistName : TextView = view.findViewById(R.id.concert_artist_name)
        val concertDate: TextView = view.findViewById(R.id.concert_date)
//        val concertSubscribeButton: Button = view.findViewById(R.id.btn_concert_subscribe)

        imageView.setImageResource(arrayList[position].ConcertPicture)
        concertName.text = arrayList[position].ConcertName
        concertArtistName.text=arrayList[position].ConcertArtistName
        concertDate.text=arrayList[position].ConcertDateTime



        return view
    }

}