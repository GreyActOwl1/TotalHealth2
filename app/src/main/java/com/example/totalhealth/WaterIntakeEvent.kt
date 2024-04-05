package com.example.totalhealth

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

//TODO: Refactor -> water_intake_table
@Entity(tableName = "water_intake_events")
//TODO: Refactor -> WaterIntakeEntity
data class WaterIntakeEvent(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "timestamp") val timestamp: LocalDateTime, // Using LocalDateTime requires Type Converters
    @ColumnInfo(name = "amount") val amount: Int // Amount of water intake per event
)