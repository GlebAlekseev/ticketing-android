package ru.alekseevjk.ticketing.feature.airline.impl.presentation.main.mapper

import ru.alekseevjk.ticketing.design.R
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.Offer
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.main.entity.OfferWithImage

fun Offer.toPopularDestinationWithImage(): OfferWithImage {
    return OfferWithImage(
        offer = this,
        imageResource = when (this.title) {
            "Die Antwoord" -> R.drawable.die_antwoord
            "Socrat&Lera" -> R.drawable.socrat_lera
            else -> R.drawable.lampabikt
        }
    )
}