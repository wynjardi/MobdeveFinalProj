package com.mobdeve.jardiniano.see

import android.widget.Filter
import java.util.logging.LogRecord

class FilterConcertUser: Filter {

    //arraylist in which we want to search
    var filterList: ArrayList<ModelConcert>
    //adapter in which filter need to be implemented
    var adapterConcertUser: AdapterConcertUser

    //constructor
    constructor(filterList: ArrayList<ModelConcert>, adapterConcertUser: AdapterConcertUser) : super(){
        this.filterList = filterList
        this.adapterConcertUser = adapterConcertUser
    }

    override fun performFiltering(constraint: CharSequence): FilterResults {

        var constraint: CharSequence? = constraint
        val results = FilterResults()

        if (constraint != null && constraint.isNotEmpty()){
            //not null or empty

            constraint = constraint.toString().uppercase()
            val filteredModels = ArrayList<ModelConcert>()
            for (i in filterList.indices){
                //validate if match
                if (filterList[i].concertName.uppercase().contains(constraint)){
                    //searched value matched with concert name, add to list
                    filteredModels.add(filterList[i])
                }
            }
            //return filtered list
            results.values = filteredModels
        }
        else{
            //either null or empty, return og list
            results.values = filterList
        }

        return results
    }

    override fun publishResults(constraint: CharSequence, results: FilterResults) {
        //apply filter changes
        adapterConcertUser.concertArrayList = results.values as ArrayList<ModelConcert>

        //notify changes
        adapterConcertUser.notifyDataSetChanged()
    }


}