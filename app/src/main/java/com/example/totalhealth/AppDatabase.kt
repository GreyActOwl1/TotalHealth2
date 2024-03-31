package com.example.totalhealth

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

@Database(entities = [FoodItemEntity::class, WaterIntakeEvent::class], version = 1)
@TypeConverters(LocalDateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodItemDao(): FoodItemDao
    abstract fun waterIntakeEventDao(): WaterIntakeEventDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        val sampleFoodItems = listOf(
            FoodItemEntity(name = "Apple", calories = 95),
            FoodItemEntity(name = "Banana", calories = 105),
            FoodItemEntity(name = "Carrot", calories = 25),
            FoodItemEntity(name = "Broccoli", calories = 55),
            FoodItemEntity(name = "Blueberries", calories = 84),
            FoodItemEntity(name = "Almonds", calories = 164),
            FoodItemEntity(name = "Chicken Breast", calories = 165),
            FoodItemEntity(name = "Brown Rice", calories = 216),
            FoodItemEntity(name = "Avocado", calories = 322),
            FoodItemEntity(name = "Salmon", calories = 206),
            FoodItemEntity(name = "Eggs", calories = 100),
        )

        private val roomDatabaseCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(IO).launch {
                    INSTANCE?.foodItemDao()?.insertAll(sampleFoodItems)
                }
            }
        }

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }


        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "Health-Total-db"
            )
                .addCallback(roomDatabaseCallback)
                .build()
    }
}

