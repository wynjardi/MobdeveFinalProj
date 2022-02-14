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

    override fun performFiltering(p0: CharSequence?): FilterResults {

    }

    override fun publishResults(p0: CharSequence?, p1: FilterResults?) {

    }


}