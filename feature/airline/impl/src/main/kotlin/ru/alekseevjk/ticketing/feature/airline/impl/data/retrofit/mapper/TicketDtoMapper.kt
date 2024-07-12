package ru.alekseevjk.ticketing.feature.airline.impl.data.retrofit.mapper

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import ru.alekseevjk.ticketing.feature.airline.impl.data.retrofit.dto.TicketDto
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.Arrival
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.Departure
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.HandLuggage
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.Luggage
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.Price
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.Ticket

fun TicketDto.toDomain(): Ticket {
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    return Ticket(
        id = this.id,
        badge = this.badge,
        price = Price(this.price.value),
        providerName = this.providerName,
        company = this.company,
        departure = Departure(
            town = this.departure.town,
            date = LocalDateTime.parse(this.departure.date, formatter),
            airport = this.departure.airport
        ),
        arrival = Arrival(
            town = this.arrival.town,
            date = LocalDateTime.parse(this.arrival.date, formatter),
            airport = this.arrival.airport
        ),
        hasTransfer = this.hasTransfer,
        hasVisaTransfer = this.hasVisaTransfer,
        luggage = Luggage(
            hasLuggage = this.luggage.hasLuggage,
            price = this.luggage.price?.value?.let { Price(it) }
        ),
        handLuggage = HandLuggage(
            hasHandLuggage = this.handLuggage.hasHandLuggage,
            size = this.handLuggage.size
        ),
        isReturnable = this.isReturnable,
        isExchangable = this.isExchangable
    )
}