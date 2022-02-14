package com.mobdeve.jardiniano.see

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.jardiniano.see.databinding.RowConcertAdminBinding

class AdapterConcertAdmin :RecyclerView.Adapter<AdapterConcertAdmin.HolderImgAdmin>  {


    private var context: Context

    //arraylist to hold imgs
    private var imgArrayList: ArrayList<ModelConcert>



    private lateinit var binding: RowConcertAdminBinding



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
        val timestap = model.timestamp

        //create timestamp to dd/MM/yyyy format
    }

    override fun getItemCount(): Int {
        return imgArrayList.size
    }

    constructor(context: Context, imgArrayList: ArrayList<ModelConcert>) : super(){
        this.context = context
        this.imgArrayList = imgArrayList
    }
    //viewholder class
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