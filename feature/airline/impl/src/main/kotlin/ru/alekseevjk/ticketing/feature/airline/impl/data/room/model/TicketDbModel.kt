package ru.alekseevjk.ticketing.feature.airline.impl.data.room.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.Arrival
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.Departure
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.HandLuggage
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.Price

@Entity
data class TicketDbModel(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "badge")
    val badge: String?,
    @Embedded(prefix = "price_")
    val price: Price,
    @ColumnInfo(name = "provider_name")
    val providerName: String,
    @ColumnInfo(name = "company")
    val company: String,
    @Embedded(prefix = "departure_")
    val departure: Departure,
    @Embedded(prefix = "arrival_")
    val arrival: Arrival,
    @ColumnInfo(name = "has_transfer")
    val hasTransfer: Boolean,
    @ColumnInfo(name = "has_visa_transfer")
    val hasVisaTransfer: Boolean,
    @ColumnInfo(name = "luggage_has_luggage")
    val hasLuggage: Boolean,
    @Embedded(prefix = "luggage_")
    val luggagePrice: Price?,
    @Embedded(prefix = "hand_luggage_")
    val handLuggage: HandLuggage,
    @ColumnInfo(name = "is_returnable")
    val isReturnable: Boolean,
    @ColumnInfo(name = "is_exchangable")
    val isExchangable: Boolean,
    @ColumnInfo(name = "cached_at")
    val cachedAt: LocalDateTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
)