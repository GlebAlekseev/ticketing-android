package ru.alekseevjk.ticketing.feature.airline.impl.data.retrofit.mapper

import ru.alekseevjk.ticketing.feature.airline.impl.data.retrofit.dto.TicketOfferDto
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.Price
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.TicketOffer

fun TicketOfferDto.toDomain(): TicketOffer {
    return TicketOffer(
        id = this.id,
        title = this.title,
        timeRange = this.timeRange,
        price = Price(this.price.value),
    )
}