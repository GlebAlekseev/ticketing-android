package ru.alekseevjk.ticketing.feature.airline.impl.data.room.converter

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

class LocalDateTimeConverter {
    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime): Long {
        return value.toInstant(TimeZone.currentSystemDefault()).epochSeconds
    }

    @TypeConverter
    fun toLocalDateTime(value: Long): LocalDateTime {
        return Instant
            .fromEpochSeconds(value)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    }
}