package ru.alekseevjk.ticketing.feature.airline.impl.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDate
import ru.alekseevjk.ticketing.core.common.Resource
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.TicketOffer
import ru.alekseevjk.ticketing.feature.airline.impl.domain.repository.TicketRepository
import javax.inject.Inject

class GetTicketsOffersUseCase @Inject constructor(
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
    ): Flow<Resource<List<TicketOffer>>> {
        return ticketRepository.getTicketsOffers(
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
}