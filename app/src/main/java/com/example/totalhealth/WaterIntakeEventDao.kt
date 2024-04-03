package com.example.totalhealth

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.time.LocalTime

@Dao
interface WaterIntakeEventDao {

    /** Insert New Water Intake Event*/
    @Insert
    suspend fun insert(event: WaterIntakeEvent): Long

    /** Insert List of Water Intake Events*/
    @Insert
    suspend fun insertAll(events: List<WaterIntakeEvent>): List<Long>


    /** Get All Water Intake Events*/
    @Query("SELECT * FROM water_intake_events")
    fun getAllEvents(): Flow<List<WaterIntakeEvent>>

    /**
     * Queries the database for the total water intake for a given day.
     *
     * @param startOfDay The start of the day. Defaults to the beginning of
     * the current day.
     * @param endOfDay The end of the day. Defaults to the end of the current day.
     * @return A Flow of the total intake for the day.
     */
    @Query(
        "SELECT SUM(amount)" +
                "FROM water_intake_events" +
                " WHERE timestamp BETWEEN :startOfDay AND :endOfDay"
    )
    fun getTotalIntakeForDay(
        startOfDay: LocalDateTime = LocalDateTime.now().with(LocalTime.MIN),
        endOfDay: LocalDateTime = LocalDateTime.now().with(LocalTime.MAX)
    ): Flow<Int?>

    /** * Queries the database for the total water intake  for all time*/
    @Query("SELECT SUM(amount) FROM water_intake_events")
    fun getTotalIntake(): Flow<Int?>

    /** * Queries the database for average water intake  for all time*/
    @Query("SELECT AVG(amount) FROM water_intake_events")
    fun getAverageWaterIntake(): Flow<Int?>


    /** * Clear all water intake events*/
    @Query("DELETE FROM water_intake_events")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(event: WaterIntakeEvent)

    /* Queries the database for the minimum water intake for all time*/
    @Query("SELECT MIN(amount) FROM water_intake_events")
    fun getMinWaterIntake(): Flow<Int?>

    /* Queries the database for the maximum water intake for all time*/
    @Query("SELECT MAX(amount) FROM water_intake_events")
    fun getMaxWaterIntake(): Flow<Int?>

}

