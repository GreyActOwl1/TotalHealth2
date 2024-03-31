package com.example.totalhealth

import android.os.Bundle
import android.widget.Button
import android.widget.EditText

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class AddFoodItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_food_entry)

        val foodNameEditText = findViewById<EditText>(R.id.food_name_edit_text)
        val foodCaloriesEditText = findViewById<EditText>(R.id.calories_edit_text)
        val submitButton = findViewById<Button>(R.id.submit_food_item_button)

        submitButton.setOnClickListener {
            val foodName = foodNameEditText.text.toString()
            val foodCalories = foodCaloriesEditText.text.toString().toIntOrNull() ?: 0
//            val foodItem = FoodItem(foodName, foodCalories)
            val foodItemEntity = FoodItemEntity(name = foodName, calories = foodCalories)


            // add to database
            val db = (application as HealthApplication).db
            insertFoodItem(db.foodItemDao(), foodItemEntity)


//            MainActivity.foodItems.add(foodItem)
            finish()
        }

    }

    private fun insertFoodItem(dao: FoodItemDao, foodItemEntity: FoodItemEntity) {
        // Launching a new coroutine to insert the item asynchronously
        lifecycleScope.launch(IO) {
            dao.insertAll(listOf(foodItemEntity))
        }
    }

}