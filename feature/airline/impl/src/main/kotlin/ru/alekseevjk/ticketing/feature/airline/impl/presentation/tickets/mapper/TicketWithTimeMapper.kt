package ru.alekseevjk.ticketing.feature.airline.impl.presentation.tickets.mapper

import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.Ticket
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.tickets.entity.TicketWithTime

fun Ticket.toTicketWithTime(): TicketWithTime {
    return TicketWithTime(
        ticket = this,
        travellingTime = computeTravellingTime(this.departure.date, this.arrival.date)
    )
}

private fun computeTravellingTime(
    departureDateTime: LocalDateTime,
    arrivalDateTime: LocalDateTime,
): Float {
    require(arrivalDateTime.isAfter(departureDateTime)) { "arrivalDateTime должен быть позже departureDateTime" }
    val duration = Duration.between(departureDateTime, arrivalDateTime)
    val hours = duration.toHours()
    val minutes = duration.toMinutes() % 60
    val fractionalHours = hours + (minutes.toFloat() / 60)
    return fractionalHours
}

