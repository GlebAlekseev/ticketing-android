package ru.alekseevjk.ticketing.feature.airline.impl.data.room.converter

import androidx.room.TypeConverter
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset

class LocalDateTime2Converter {
    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime): Long {
        return value.toEpochSecond(ZoneOffset.UTC)
    }

    @TypeConverter
    fun toLocalDateTime(value: Long): LocalDateTime {
        return value.let { LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC) }
    }
}