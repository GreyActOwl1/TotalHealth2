package com.example.totalhealth

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import java.time.LocalDateTime
import java.time.LocalTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

@Database(entities = [FoodItemEntity::class, WaterIntakeEvent::class], version = 1)
@TypeConverters(LocalDateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodItemDao(): FoodItemDao
    abstract fun waterIntakeEventDao(): WaterIntakeEventDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        val sampleFoodItems =
                listOf(
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

        val sampleWaterIntakeEvents =
                listOf(
                        WaterIntakeEvent(
                                timestamp = LocalDateTime.of(2024, 3, 30, 12, 0),
                                amount = 10
                        ),
                        WaterIntakeEvent(
                                timestamp = LocalDateTime.of(2024, 3, 30, 12, 15),
                                amount = 20
                        ),
                        WaterIntakeEvent(
                                timestamp = LocalDateTime.of(2024, 3, 30, 12, 30),
                                amount = 30
                        ),
                        WaterIntakeEvent(
                                timestamp = LocalDateTime.of(2024, 3, 30, 12, 45),
                                amount = 40
                        ),
                        WaterIntakeEvent(
                                timestamp = LocalDateTime.of(2024, 3, 30, 13, 0),
                                amount = 50
                        ),
                        WaterIntakeEvent(
                                timestamp = LocalDateTime.now().with(LocalTime.MIN),
                                amount = 61
                        ),
                        WaterIntakeEvent(
                                timestamp = LocalDateTime.now().with(LocalTime.of(0, 0, 7)),
                                amount = 62
                        ),
                        WaterIntakeEvent(
                                timestamp = LocalDateTime.now().with(LocalTime.of(0, 0, 14)),
                                amount = 63
                        ),
                )

        private val roomDatabaseCallback =
                object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        CoroutineScope(IO).launch {
                            INSTANCE?.foodItemDao()?.insertAll(sampleFoodItems)
                            INSTANCE?.waterIntakeEventDao()?.insertAll(sampleWaterIntakeEvents)
                        }
                    }
                }

        fun getInstance(context: Context): AppDatabase =
                INSTANCE
                        ?: synchronized(this) {
                            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(
                                context.applicationContext,
                                AppDatabase::class.java,
                                "Health-Total-db"
                        )
                        .addCallback(roomDatabaseCallback)
                        .build()
    }
}
