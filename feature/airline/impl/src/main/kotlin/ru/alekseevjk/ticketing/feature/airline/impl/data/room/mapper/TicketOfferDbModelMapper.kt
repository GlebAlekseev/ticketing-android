package ru.alekseevjk.ticketing.feature.airline.impl.data.room.mapper

import ru.alekseevjk.ticketing.feature.airline.impl.data.room.model.TicketOfferDbModel
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.TicketOffer

fun TicketOfferDbModel.toDomain(): TicketOffer {
    return TicketOffer(
        id = this.id,
        title = this.title,
        timeRange = this.timeRange,
        price = this.price,
    )
}

fun TicketOffer.toDbModel(): TicketOfferDbModel {
    return TicketOfferDbModel(
        id = this.id,
        title = this.title,
        timeRange = this.timeRange,
        price = this.price,
    )
}