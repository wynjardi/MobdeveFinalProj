package com.mobdeve.jardiniano.see

import android.content.Context
import android.text.Layout
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.jardiniano.see.databinding.RowConcertAdminBinding

class AdapterConcertAdmin :RecyclerView.Adapter<AdapterConcertAdmin.HolderImgAdmin>, Filterable {


    private var context: Context

    //arraylist to hold imgs
    public var imgArrayList: ArrayList<ModelConcert>
    private val filterList:ArrayList<ModelConcert>


    private lateinit var binding: RowConcertAdminBinding


    var filter: FilterConcertAdmin? = null
    constructor(context: Context, imgArrayList: ArrayList<ModelConcert>) : super(){
        this.context = context
        this.imgArrayList = imgArrayList
        this.filterList = imgArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderImgAdmin {
        binding = RowConcertAdminBinding.inflate(LayoutInflater.from(context), parent, false)

        return HolderImgAdmin(binding.root)
    }

    override fun onBindViewHolder(holder: HolderImgAdmin, position: Int) {


        val model = imgArrayList[position]
        val imgId = model.id
        val categoryId = model.categoryId
        val concertTitle = model.concertName
        val concertArtistName= model.concertArtist
        val imgUrl = model.imageUrl
        val timestamp = model.timestamp

        //create timestamp to dd/MM/yyyy format

        val formattedDate = MyApplication.formatTimeStamp(timestamp)

        //set data
        holder.concertTitleTv.text = concertTitle
        holder.concertArtistNameTv.text = concertArtistName
        holder.dateBtn.text = formattedDate

        //load further details

        //categ id
        MyApplication.loadCategory(categoryId = categoryId, holder.categoryTv)

        MyApplication.loadConcertFromUrlSinglePage(imgUrl , concertTitle, holder.imageView, holder.progressBar)
    }

    override fun getItemCount(): Int {
        return imgArrayList.size
    }


    //viewholder class


    override fun getFilter(): Filter {
        if (filter == null){
            filter = FilterConcertAdmin(filterList, this)

        }

        return filter as FilterConcertAdmin
    }

    inner class HolderImgAdmin(itemView: View): RecyclerView.ViewHolder(itemView){

        val imageView = binding.imageView
        val progressBar = binding.progressBar
        val concertTitleTv = binding.titleTv
        val concertArtistNameTv = binding.descriptionTv
        val categoryTv = binding.categoryTv
        val dateBtn = binding.dateTv
        val moreBtn = binding.moreBtn

    }
}