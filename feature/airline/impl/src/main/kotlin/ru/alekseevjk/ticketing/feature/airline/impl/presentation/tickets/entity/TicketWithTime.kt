package ru.alekseevjk.ticketing.feature.airline.impl.presentation.tickets.entity

import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.Ticket

data class TicketWithTime(
    val ticket: Ticket,
    val travellingTime: Float,
)