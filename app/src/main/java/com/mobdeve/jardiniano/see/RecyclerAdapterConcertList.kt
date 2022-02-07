package com.mobdeve.jardiniano.see


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class  RecyclerAdapterConcertList (
) : RecyclerView.Adapter<RecyclerAdapterConcertList.SimpleViewHolder>() {

    private var concertImagesTitles = arrayOf(R.drawable.harry_styles_picture, R.drawable.billie_eilish_picture,R.drawable.taylor_swift_picture)
    private var concertNameTitles = arrayOf("Love on Tour", "When We All Fall Asleep Tour", "Fearless Tour")
    private var concertArtistName = arrayOf("Harry Styles", "Billie Eilish", "Taylor Swift")
    private var concertDateTitles = arrayOf("November 20, 2021", "July 06, 2021", "March 16, 2020")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val itemBinding = LayoutInflater.from(parent.context).inflate(R.layout.item_row_concert, parent, false)
        return SimpleViewHolder(itemBinding)

    }

    override fun getItemCount(): Int {
        return concertNameTitles.size

    }

    override fun onBindViewHolder(
        holder: RecyclerAdapterConcertList.SimpleViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        holder.concertimg.setImageResource(concertImagesTitles[position])
        holder.concertname.text = concertNameTitles[position]
        holder.concertartist.text = concertArtistName[position]
        holder.concertdate.text = concertDateTitles[position]

    }
    inner class SimpleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var concertimg: ImageView
        var concertname: TextView
        var concertartist: TextView
        var concertdate: TextView
        var subscribe: Button


        init{
            concertimg = itemView.findViewById(R.id.concert_img)
            concertname = itemView.findViewById(R.id.concert_name)
            concertartist = itemView.findViewById(R.id.concert_artist_name)
            concertdate = itemView.findViewById(R.id.concert_date)
            subscribe = itemView.findViewById(R.id.btn_concert_subscribe)

        }

    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {


    }
}




