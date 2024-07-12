package ru.alekseevjk.ticketing.feature.airline.impl.data.room.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.Price

@Entity
data class OfferDbModel(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "town")
    val town: String,
    @Embedded(prefix = "price_")
    val price: Price,
    @ColumnInfo(name = "cached_at")
    val cachedAt: LocalDateTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
)