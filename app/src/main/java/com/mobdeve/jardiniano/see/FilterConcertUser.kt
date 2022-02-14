package com.mobdeve.jardiniano.see

import java.util.logging.Filter
import java.util.logging.LogRecord

class FilterConcertUser: Filter {

    //arraylist in which we want to search
    var filterList: ArrayList<ModelConcert>
    //adapter in which filter need to be implemented
    var adapterConcertUser: AdapterConcertUser

    //constructor
    constructor(filterList: ArrayList<ModelConcert>, adapterConcertUser: AdapterConcertUser) {
        this.filterList = filterList
        this.adapterConcertUser = adapterConcertUser
    }

    override fun isLoggable(p0: LogRecord?): Boolean {
        TODO("Not yet implemented")
    }

}