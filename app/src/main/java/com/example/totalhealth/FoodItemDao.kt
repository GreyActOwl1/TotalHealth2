package com.example.totalhealth

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodItemDao {
    // Get all entries
    @Query("SELECT * FROM food_item_table")
    fun getAll(): Flow<List<FoodItemEntity>>

    // Insert all entries
    @Insert
    fun insertAll(foodItems: List<FoodItemEntity>)

    // Insert entry
    @Insert
    fun insert(foodItem: FoodItemEntity)

    // Delete all entries
    @Query("DELETE FROM food_item_table")
    fun deleteAll()

    // Delete entry
    @Delete
    fun delete(foodItem: FoodItemEntity)

    // Edit entry name
    @Query("UPDATE food_item_table SET foodName = :name WHERE id = :id")
    fun updateName(id: Long, name: String)

    // Edit entry calories
    @Query("UPDATE food_item_table SET foodCalories = :calories WHERE id = :id")
    fun updateCalories(id: Long, calories: Int)

    // Get entry
    @Query("SELECT * FROM food_item_table WHERE id = :id")
    fun getEntry(id: Long): Flow<FoodItemEntity>

    //Get total calories
    @Query("SELECT SUM(foodCalories) FROM food_item_table")
    fun getTotalCalories(): Flow<Int?>
}
