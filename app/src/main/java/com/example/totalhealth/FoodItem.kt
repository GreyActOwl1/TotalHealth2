package com.example.totalhealth

class FoodItem(var name: String, var calories: Int = 0) {
    companion object {
        val foodNames = listOf(
            "Apple", "Banana", "Bread", "Milk", "Eggs"
        )
        val foodCalories = listOf(50, 100, 120, 150, 101)
        val foodItems = mutableListOf<FoodItem>()

        fun getItems(): MutableList<FoodItem> {
            // Check if foodItems is empty to avoid adding the same items multiple times
            if (foodItems.isEmpty()) {
                foodItems.addAll(foodNames.zip(foodCalories).map { FoodItem(it.first, it.second) })
            }
            return foodItems
        }

        fun getTotalCalories(): Int = getItems().sumOf { it.calories }


    }
}


