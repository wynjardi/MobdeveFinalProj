package com.mobdeve.jardiniano.see

import android.widget.Filter

class FilterConcertAdmin : Filter {

    private lateinit var filterList: ArrayList<ModelConcert>

    private lateinit var adapterConcertAdmin : AdapterConcertAdmin

    constructor()
    constructor(filterList: ArrayList<ModelConcert>, adapterConcertADmin: AdapterConcertAdmin) {
        this.filterList = filterList
        this.adapterConcertAdmin = adapterConcertADmin
    }

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        var constraint: CharSequence? = constraint // value to search
        val results = FilterResults()

        //value should not be null and empty
        if (constraint != null && constraint.isEmpty()) {
            constraint = constraint.toString().lowercase()
            var filteredModels = ArrayList<ModelConcert>()
            for (i in filterList.indices) {

                if (filterList[i].concertName.lowercase().contains(constraint)){
                //searched value is similar to value in list add to filtered list
                    filteredModels.add(filterList[i])
            }
        }
        results.count = filteredModels.size
        results.values = filteredModels
        }
        else{
            results.count = filterList.size
            results.values = filterList

        }
        return results //
    }

    override fun publishResults(constraint: CharSequence, results: FilterResults) {
        adapterConcertAdmin.imgArrayList = results.values as ArrayList<ModelConcert>

        //notify
        adapterConcertAdmin.notifyDataSetChanged()
    }
}