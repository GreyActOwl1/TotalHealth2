package com.example.totalhealth

class FoodItem(val name: String, val calories: Int = 0){
    companion object{
        val foodNames = listOf(
            "Apple","Banana","Bread","Milk", "Eggs"
        )
        val foodCalories = listOf(50,100, 120, 150, 101)

        fun getItems(): List<FoodItem> = foodNames.zip(foodCalories).map { FoodItem(it.first, it.second) }
    }
}


