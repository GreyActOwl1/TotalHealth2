package com.example.totalhealth

import android.os.Bundle
import android.widget.Button
import android.widget.EditText

import androidx.appcompat.app.AppCompatActivity

class AddFoodItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_food_entry)

        val foodNameEditText = findViewById<EditText>(R.id.food_name_edit_text)
        val foodCaloriesEditText = findViewById<EditText>(R.id.calories_edit_text)
        val submitButton = findViewById<Button>(R.id.submit_food_item_button)

        submitButton.setOnClickListener {
            val foodName = foodNameEditText.text.toString()
            val foodCalories = foodCaloriesEditText.text.toString().toInt()
            val foodItem = FoodItem(foodName, foodCalories)
            MainActivity.foodItems.add(foodItem)
            // TODO:Add food item to database
            finish()
        }








    }

}