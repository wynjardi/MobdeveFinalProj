package com.mobdeve.jardiniano.see

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.Hold
import com.mobdeve.jardiniano.see.databinding.RowConcertUserBinding

class AdapterConcertUser: RecyclerView.Adapter<AdapterConcertUser.HolderConcertUser>, Filterable {

    //context, get  using constructor
    private var context: Context

    //to change, arraylist to hold concerts, get using constructor
    public var concertArrayList: ArrayList<ModelConcert>
    //array list to hold filtered concerts
    private var filterList: ArrayList<ModelConcert>

    //arraylist for holding filtered concerts
//    private var filterList: ArrayList<ModelConcert>

    private var filter: FilterConcertUser? = null


    //viewVinding row_concert_user.xml
    private lateinit var binding: RowConcertUserBinding

    constructor(context: Context, concertArrayList: ArrayList<ModelConcert>) {
        this.context = context
        this.concertArrayList = concertArrayList
        this.filterList = concertArrayList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderConcertUser {
        //inflate/bind layout row_concert_pdf
        binding = RowConcertUserBinding.inflate(LayoutInflater.from(context), parent, false)

        return HolderConcertUser(binding.root)

    }

    override fun onBindViewHolder(holder: HolderConcertUser, position: Int) {
        //gets data, sets data, handles clicks, etc

        //get data
        val model = concertArrayList[position]
        val concertId = model.id
        var categoryId = model.categoryId
        var title = model.concertName
        var description = model.concertArtist
        var uid = model.uid
        var url = model.imageUrl
        var timestamp = model.timestamp

        //convert time to change
        val date = MyApplication.formatTimeStamp(timestamp)

        //set data, insert code for date
        holder.titleTv.text = title
        holder.descriptionTv.text = description
        holder.dateTv.text = date

        MyApplication.loadConcertFromUrlSinglePage(url , title, holder.imageView, holder.progressBar)

        MyApplication.loadCategory(categoryId, holder.categoryTv)


        //handle click, opens concert details page
        holder.itemView.setOnClickListener{
            //pass concert id in intent then get concert info
            val intent = Intent(context, ConcertDetailActivity::class.java)
            intent.putExtra("concertid", concertId)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return concertArrayList.size //returns list size/number of records found
    }

    override fun getFilter(): Filter {
        if (filter == null){
            filter = FilterConcertUser(filterList, this)
        }
        return filter as FilterConcertUser
    }

    //to change, view holder of class row_concert_user.xml
    inner class HolderConcertUser(itemView: View): RecyclerView.ViewHolder(itemView){
    //init UI components of class row_concert_user.xml

        //concert image
        val imageView = binding.imageView

        var progressBar = binding.progressBar
        var titleTv = binding.titleTv
        var descriptionTv = binding.descriptionTv
        var categoryTv = binding.categoryTv
        var dateTv = binding.dateTv


    }



}