package com.mobdeve.jardiniano.see

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mobdeve.jardiniano.see.databinding.RowConcertSubscribeBinding

class AdapterConcertSubscription : RecyclerView.Adapter<AdapterConcertSubscription.HolderConcertSubscribe> {

    //context
    private val context: Context

    //arraylist to hold concerts
    private var concertArrayList: ArrayList<ModelConcert>

    //view binding
    private lateinit var binding: RowConcertSubscribeBinding

    //constructor
    constructor(context: Context, concertArrayList: ArrayList<ModelConcert>) {
        this.context = context
        this.concertArrayList = concertArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderConcertSubscribe {
        //hind or inlfate row fav xml
        binding= RowConcertSubscribeBinding.inflate(LayoutInflater.from(context), parent, false)

        return HolderConcertSubscribe(binding.root)
    }

    override fun onBindViewHolder(holder: HolderConcertSubscribe, position: Int) {
        //get data, set data

        val model = concertArrayList[position]
        loadConcertDetails(model, holder)
    }

    private fun loadConcertDetails(model: ModelConcert, holder: AdapterConcertSubscription.HolderConcertSubscribe) {
        val concertId = model.id
        val ref = FirebaseDatabase.getInstance().getReference("")
        ref.child(concertId)
            .addListenerForSingleValueEvent(object; ValueEventListener){
                override fun onDataChange (snapshot: DataSnapshot) {
                    //get base info
                    val concertId = "${snapshot.child("concertId").value}"

                    val description = "${snapshot.child("concertId").value}"
                    val concertId = "${snapshot.child("concertId").value}"
                }
        }

    }

    override fun getItemCount(): Int {
        return concertArrayList.size //returns number of items in sub list
    }

    //view holder class to manage ui view of row_concert_sub xml
    inner class HolderConcertSubscribe(itemView: View): RecyclerView.ViewHolder(itemView){
        //init ui views
        var imageView = binding.imageView
        var progressBar = binding.progressBar
        var removeSubBtn = binding.removeSubBtn
        var descriptionTv = binding.descriptionTv
        var categoryTv = binding.categoryTv
        var dateTv = binding.dateTv
    }

}