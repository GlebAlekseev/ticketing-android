package ru.alekseevjk.ticketing.feature.airline.impl.data.room.mapper

import ru.alekseevjk.ticketing.feature.airline.impl.data.room.model.TicketDbModel
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.Luggage
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.Ticket

fun TicketDbModel.toDomain(): Ticket {
    return Ticket(
        id = this.id,
        badge = this.badge,
        price = this.price,
        providerName = this.providerName,
        company = this.company,
        departure = this.departure,
        arrival = this.arrival,
        hasTransfer = this.hasTransfer,
        hasVisaTransfer = this.hasVisaTransfer,
        luggage = Luggage(
            hasLuggage = this.hasLuggage,
            price = this.luggagePrice
        ),
        handLuggage = this.handLuggage,
        isReturnable = this.isReturnable,
        isExchangable = this.isExchangable
    )
}

fun Ticket.toDbModel(): TicketDbModel {
    return TicketDbModel(
        id = this.id,
        badge = this.badge,
        price = this.price,
        providerName = this.providerName,
        company = this.company,
        departure = this.departure,
        arrival = this.arrival,
        hasTransfer = this.hasTransfer,
        hasVisaTransfer = this.hasVisaTransfer,
        hasLuggage = this.luggage.hasLuggage,
        luggagePrice = this.luggage.price,
        handLuggage = this.handLuggage,
        isReturnable = this.isReturnable,
        isExchangable = this.isExchangable
    )
}