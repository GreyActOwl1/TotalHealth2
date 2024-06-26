package com.example.totalhealth

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeConverter {
    companion object {
        private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

        @TypeConverter
        @JvmStatic
        fun toLocalDateTime(value: String?): LocalDateTime? {
            return value?.let {
                return formatter.parse(value, LocalDateTime::from)
            }
        }

        @TypeConverter
        @JvmStatic
        fun fromLocalDateTime(date: LocalDateTime?): String? {
            return date?.format(formatter)
        }
    }
}
