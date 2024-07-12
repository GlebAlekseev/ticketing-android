package ru.alekseevjk.ticketing.feature.airline.impl.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDate
import ru.alekseevjk.ticketing.core.common.Resource
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.Ticket
import ru.alekseevjk.ticketing.feature.airline.impl.domain.repository.TicketRepository
import javax.inject.Inject

class GetTicketsUseCase @Inject constructor(
    private val ticketRepository: TicketRepository
) {
    operator fun invoke(
        fromCity: String,
        toCity: String,
        directOnly: Boolean,
        withLuggageOnly: Boolean,
        departureDate: LocalDate,
        returnDate: LocalDate?,
        travelClass: String?,
        passengerCount: Int?
    ): Flow<Resource<List<Ticket>>> = ticketRepository.getTickets(
        fromCity = fromCity,
        toCity = toCity,
        directOnly = directOnly,
        withLuggageOnly = withLuggageOnly,
        departureDate = departureDate,
        returnDate = returnDate,
        travelClass = travelClass,
        passengerCount = passengerCount
    )
}