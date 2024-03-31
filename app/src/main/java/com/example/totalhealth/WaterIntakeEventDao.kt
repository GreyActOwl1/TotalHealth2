package com.example.totalhealth

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalDateTime

@Dao
interface WaterIntakeEventDao {
    // Insert a new water intake event
    @Insert
    fun insert(event: WaterIntakeEvent)

    // Get all water intake events
    @Query("SELECT * FROM water_intake_events")
    fun getAllEvents(): Flow<List<WaterIntakeEvent>>

    // Get total water intake for a specific day
    @Query("SELECT SUM(amount) FROM water_intake_events WHERE timestamp BETWEEN :startOfDay AND :endOfDay")
    fun getTotalIntakeForDay(startOfDay: LocalDateTime, endOfDay: LocalDateTime): Flow<Int?>

    // Get total water intake for all time
    @Query("SELECT SUM(amount) FROM water_intake_events")
    fun getTotalIntake(): Flow<Int?>

    // Optional: Delete all events (for example, to reset the database)
    @Query("DELETE FROM water_intake_events")
    fun deleteAll()
}
