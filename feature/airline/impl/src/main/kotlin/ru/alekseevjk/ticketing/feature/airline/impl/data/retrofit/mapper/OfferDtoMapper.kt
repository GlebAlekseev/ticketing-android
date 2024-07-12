package ru.alekseevjk.ticketing.feature.airline.impl.data.retrofit.mapper

import ru.alekseevjk.ticketing.feature.airline.impl.data.retrofit.dto.OfferDto
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.Offer
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.Price

fun OfferDto.toDomain(): Offer {
    return Offer(
        id = this.id,
        title = this.title,
        town = this.town,
        price = Price(this.price.value),
    )
}