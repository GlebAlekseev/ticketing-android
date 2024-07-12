package ru.alekseevjk.ticketing.feature.airline.impl.domain.repository

import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDate
import ru.alekseevjk.ticketing.core.common.Resource
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.Ticket
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.TicketOffer

interface TicketRepository {
    fun getTicketsOffers(
        fromCity: String,
        toCity: String,
        directOnly: Boolean,
        withLuggageOnly: Boolean,
        departureDate: LocalDate,
        returnDate: LocalDate?,
        travelClass: String?,
        passengerCount: Int?
    ): Flow<Resource<List<TicketOffer>>>

    fun getTickets(
        fromCity: String,
        toCity: String,
        directOnly: Boolean,
        withLuggageOnly: Boolean,
        departureDate: LocalDate,
        returnDate: LocalDate?,
        travelClass: String?,
        passengerCount: Int?
    ): Flow<Resource<List<Ticket>>>
}