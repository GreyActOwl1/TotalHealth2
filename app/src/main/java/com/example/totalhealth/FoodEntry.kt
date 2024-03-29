package com.example.totalhealth

class FoodEntry(val name: String, val calories: Int = 0){
    companion object{
        val foodNames = listOf(
            "Apple","Banana","Bread","Milk", "Eggs"
        )
        val foodCalories = listOf(50,100, 120, 150, 101)

        fun getItems(): List<FoodEntry> = foodNames.zip(foodCalories).map { FoodEntry(it.first, it.second) }
    }
}


