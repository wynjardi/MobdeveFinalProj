//package com.mobdeve.jardiniano.see
//
//
//import android.content.Intent
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.*
//import androidx.core.content.ContextCompat.startActivity
//import androidx.recyclerview.widget.RecyclerView
//import com.mobdeve.jardiniano.see.databinding.ItemRowConcertBinding
//import com.mobdeve.jardiniano.see.model.OnItemClickListener
//
//// This is for the recyclerview nung concert posters and short details
//
//
//
//class  RecyclerAdapterConcertList(
//    private var items: ArrayList<Concerts?>,
//    private var clickListner: OnItemClickListen
//) : RecyclerView.Adapter<RecyclerAdapterConcertList.SimpleViewHolder>() {
//
//    interface OnItemClickListen{
//        fun onLoadClick(position: Int)
//    }
//
//
//    private var concertImagesTitles = arrayOf(R.drawable.harry_styles_picture, R.drawable.billie_eilish_picture,R.drawable.taylor_swift_picture)
//    private var concertNameTitles = arrayOf("Love on Tour", "When We All Fall Asleep Tour", "Fearless Tour")
//    private var concertArtistName = arrayOf("Harry Styles", "Billie Eilish", "Taylor Swift")
//    private var concertDateTitles = arrayOf("November 20, 2021 8PM TO 11PM", "July 06, 2021 8PM TO 11PM", "March 16, 20208PM TO 11PM")
//    private var concertPricesTitles= arrayOf("Total prices are as follows: P20,000 P10,000 P5,000", "Total prices are as follows: P30,000 P15,000 P5,000", "Total prices are as follows: P20,000 P10,000 P5,000")
//    private var concertPlaceTitles = arrayOf("Mall of Asia Arena, Pasay City", "Araneta Coliseum, Quezon City", "Kita Theatre, Quezon City")
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
//        val itemBinding = ItemRowConcertBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return SimpleViewHolder(itemBinding)
//
//    }
//
//    override fun getItemCount(): Int {
//        return concertNameTitles.size
//
//    }
//
//    override fun onBindViewHolder(
//        holder: RecyclerAdapterConcertList.SimpleViewHolder,
//        position: Int,
//        payloads: MutableList<Any>
//    ) {
//        holder.concertimg.setImageResource(concertImagesTitles[position])
//        holder.concertname.text = concertNameTitles[position]
//        holder.concertartist.text = concertArtistName[position]
//        holder.concertdate.text = concertDateTitles[position]
//
//
//
//    }
//
//    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
////        holder.initialize(Concerts.get(position),clickListner)
//
//    }
//
//
//     inner class SimpleViewHolder(private val itemBinding: ItemRowConcertBinding): RecyclerView.ViewHolder(itemBinding.root) {
//        var concertimg: ImageView
//        var concertname: TextView
//        var concertartist: TextView
//        var concertdate: TextView
//        var subscribe: Button
//        var viewDetails: Button
////        var concertplace: TextView
////        var concertprices: TextView
////        var concertBACK: Button
//
//
//        init {
//
//
//
//            concertname = itemView.findViewById(R.id.concert_name)
//            concertimg = itemView.findViewById(R.id.concert_img)
//            concertartist = itemView.findViewById(R.id.concert_artist_name)
//            concertdate = itemView.findViewById(R.id.concert_date)
////            concertplace = itemView.findViewById(R.id.detailed_concert_place)
////            concertprices = itemView.findViewById(R.id.detailed_concert_prices)
////            concertBACK = itemView.findViewById(R.id.back_button)
//            subscribe = itemView.findViewById(R.id.btn_concert_subscribe)
//            viewDetails = itemView.findViewById(R.id.btn_concert_view)
//
//            //listener for the view button itself
//            viewDetails.setOnClickListener{
//                val position = adapterPosition
//                clickListner.onLoadClick(position)
//            }
//
//
//        }
//        fun initialize(item: Concerts,action:OnItemClickListen){
//            concertname.text = item.ConcertName
//            concertartist.text= item.ConcertArtistName
//            concertdate.text = item.ConcertDateTime
////            concertplace.text = item.ConcertPlace
////            concertprices.text = item.ConcertPrices
//            concertimg.setImageResource(item.ConcertPicture)
//
//
//
//        }
//
//
//    }
//
//
//
//
//}
//
//
//
//
