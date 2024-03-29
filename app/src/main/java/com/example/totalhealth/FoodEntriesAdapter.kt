package com.example.totalhealth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodEntriesAdapter (private val foodEntries: List<FoodEntry>) :
    RecyclerView.Adapter<FoodEntriesAdapter.ViewHolder>(){
   inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodItemNameTextView: TextView = itemView.findViewById(R.id.food_item_name_text_view)
        val foodItemCaloriesTextView: TextView = itemView.findViewById(R.id.food_item_calories_text_view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.food_item, parent, false))
    }

    override fun getItemCount(): Int {
        return foodEntries.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val foodEntry = foodEntries[position]
        holder.foodItemNameTextView.text = foodEntry.name
        holder.foodItemCaloriesTextView.text = foodEntry.calories.toString()
    }
}