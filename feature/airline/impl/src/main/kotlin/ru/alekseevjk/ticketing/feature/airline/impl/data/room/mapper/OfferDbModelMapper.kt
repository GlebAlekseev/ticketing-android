package ru.alekseevjk.ticketing.feature.airline.impl.data.room.mapper

import ru.alekseevjk.ticketing.feature.airline.impl.data.room.model.OfferDbModel
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.Offer

fun OfferDbModel.toDomain(): Offer {
    return Offer(
        id = this.id,
        title = this.title,
        town = this.town,
        price = this.price,
    )
}

fun Offer.toDbModel(): OfferDbModel {
    return OfferDbModel(
        id = this.id,
        title = this.title,
        town = this.town,
        price = this.price,
    )
}