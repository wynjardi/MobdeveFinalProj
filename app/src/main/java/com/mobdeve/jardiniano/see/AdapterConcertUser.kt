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

class AdapterConcertUser: RecyclerView.Adapter<AdapterConcertUser.HolderImgUser>, Filterable {

    //context, get  using constructor
    private var context: Context

    //to change, arraylist to hold concerts, get using constructor
    public var imgArrayList: ArrayList<ModelConcert>
    //array list to hold filtered concerts
    private var filterList: ArrayList<ModelConcert>

    //viewVinding row_concert_user.xml
    private lateinit var binding: RowConcertUserBinding


    //arraylist for holding filtered concerts
//    private var filterList: ArrayList<ModelConcert>

    private var filter: FilterConcertUser? = null

    constructor(context: Context, imgArrayList: ArrayList<ModelConcert>): super() {
        this.context = context
        this.imgArrayList = imgArrayList
        this.filterList = imgArrayList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderImgUser {
        //inflate/bind layout row_concert_pdf
        binding = RowConcertUserBinding.inflate(LayoutInflater.from(context), parent, false)

        return HolderImgUser(binding.root)

    }

    override fun onBindViewHolder(holder: HolderImgUser, position: Int) {
        //gets data, sets data, handles clicks, etc

        //get data
        val model = imgArrayList[position]
        val concertId = model.id
        var categoryId = model.categoryId
        var concertTitle = model.concertName
        var concertArtistName = model.concertArtist
        var uid = model.uid
        var imgUrl = model.imageUrl
        var timestamp = model.timestamp

        //convert time to change
        val formattedDate = MyApplication.formatTimeStamp(timestamp)

        //set data, insert code for date
        holder.concertTitleTv.text = concertTitle
        holder.concertArtistNameTv.text = concertArtistName
        holder.dateBtn.text = formattedDate

        MyApplication.loadConcertFromUrlSinglePage(imgUrl , concertTitle, holder.imageView, holder.progressBar)

        MyApplication.loadCategory(categoryId = categoryId, holder.categoryTv)


        //handle click, opens concert details page
        holder.itemView.setOnClickListener{
            //pass concert id in intent then get concert info
            val intent = Intent(context, ConcertDetailActivity::class.java)
            intent.putExtra("concertid", concertId)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return imgArrayList.size //returns list size/number of records found
    }

    override fun getFilter(): Filter {
        if (filter == null){
            filter = FilterConcertUser(filterList, this)
        }

        return filter as FilterConcertUser
    }

    //to change, view holder of class row_concert_user.xml
    inner class HolderImgUser(itemView: View): RecyclerView.ViewHolder(itemView){
    //init UI components of class row_concert_user.xml

        //concert image
        val imageView = binding.imageView
        val progressBar = binding.progressBar
        val concertTitleTv = binding.titleTv
        val concertArtistNameTv = binding.descriptionTv
        val categoryTv = binding.categoryTv
        val dateBtn = binding.dateTv


    }



}