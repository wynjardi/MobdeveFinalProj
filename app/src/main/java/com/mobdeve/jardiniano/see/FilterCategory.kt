package com.mobdeve.jardiniano.see

import android.widget.Adapter

import android.widget.Filter

class FilterCategory: Filter {

    //araylist to search
    private var filterList: ArrayList<ModelCategory>

    //adapter to filter
    private var adapterCategory: AdapterCategory

    //constructor
    constructor(filterList: ArrayList<ModelCategory>, adapterCategory: AdapterCategory) : super() {
        this.filterList = filterList
        this.adapterCategory = adapterCategory
    }

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        var constraint = constraint
        val results = FilterResults()

        //value should not be empty
        if (constraint != null && constraint.isNotEmpty()){
            //search value nmot empty

            //change to uppser or lower case
            constraint = constraint.toString().uppercase()
            val filteredModel:ArrayList<ModelCategory> = ArrayList()
            for (i in 0 until filterList.size){
                //validate
                if (filterList[i].category.uppercase().contains(constraint)){
                    //add to filtered list
                    filteredModel.add(filterList[i])
                }
            }
            results.count = filteredModel.size
            results.values = filteredModel

        }
        else{
            //search value is either null or empty
            results.count = filterList.size
            results.values = filterList
        }
        return results
    }


    override fun publishResults(constraint: CharSequence?, results: FilterResults) {
        //apply filter changes
        adapterCategory.categoryArrayList = results.values as ArrayList<ModelCategory>

        //notify
        adapterCategory.notifyDataSetChanged()


    }


}