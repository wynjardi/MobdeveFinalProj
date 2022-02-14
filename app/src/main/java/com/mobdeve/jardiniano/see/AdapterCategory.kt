package com.mobdeve.jardiniano.see

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.mobdeve.jardiniano.see.databinding.RowCategoryBinding

class AdapterCategory :RecyclerView.Adapter<AdapterCategory.HolderCategory>, Filterable {


    private val context: Context
    public var categoryArrayList: ArrayList<ModelCategory>
    private var filterList: ArrayList<ModelCategory>

    private var filter: FilterCategory? = null

    //constructor


    private lateinit var binding: RowCategoryBinding

    constructor(
        context: Context,
        categoryArrayList: ArrayList<ModelCategory>
    ) {
        this.context = context
        this.categoryArrayList = categoryArrayList
        this.filterList= categoryArrayList

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderCategory {
        //inflate bind row categ
        binding = RowCategoryBinding.inflate(LayoutInflater.from(context), parent, false)

        return HolderCategory(binding.root)
    }

    override fun onBindViewHolder(holder: HolderCategory, position: Int) {

        //get data
        val model = categoryArrayList[position]
        val id = model.id
        val category = model.category
        val uid = model.uid
        val timestamp = model.timestamp

        //set data
        holder.categoryTv.text = category


        //delete categ
        holder.deleteBtn.setOnClickListener {
            //confirm before delete
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete")
                .setMessage("Delete this category?")
                .setPositiveButton("Confirm"){a, d->
                    Toast.makeText(context, "Deleting", Toast.LENGTH_SHORT).show()
                    deleteCategory(model, holder)
                }
                .setNegativeButton("Cancel"){a, d->
                    a.dismiss()

                }
                .show()
        }

        //handle click start concert list admin act, has concert id, name, artist name
        holder.itemView.setOnClickListener{
            val intent = Intent(context, ConcertListAdminActivity::class.java)
            intent.putExtra("categoryId", id)
            intent.putExtra("category", category)
            context.startActivity(intent)
        }

    }

    fun deleteCategory(model: ModelCategory, holder: HolderCategory) {
        //get id of categ
        val id = model.id

        //firebase categ -> categ id
        val ref =FirebaseDatabase.getInstance().getReference("Categories")
        ref.child(id)
            .removeValue()
            .addOnSuccessListener {
                Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e->
                Toast.makeText(context, "Unable to delete", Toast.LENGTH_SHORT).show()
            }

    }

    override fun getItemCount(): Int {
        return categoryArrayList.size
    }


    //Viewholder class to hold/init UI views for row categ
    inner class HolderCategory(itemView: View): RecyclerView.ViewHolder(itemView){

        var categoryTv: TextView = binding.categoryTv
        var deleteBtn: Button = binding.deleteBtn
    }

    override fun getFilter(): Filter {
        if (filter == null){
            filter = FilterCategory(filterList, this)
        }
        return filter as FilterCategory

    }


}