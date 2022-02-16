package com.mobdeve.jardiniano.see

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class UserCategoryAdapter(private val context: Context):
    RecyclerView.Adapter<UserCategoryAdapter.ViewHolder>() {

    var items: List<ModelCategory> = listOf()

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val button: Button = view.findViewById(R.id.button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.button.text = item.category

        holder.button.setOnClickListener {
            (context as DashboardUserActivity).filterByCategory(item.id)
        }
    }

    override fun getItemCount(): Int = items.count()

}