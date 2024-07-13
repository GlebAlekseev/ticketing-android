package ru.alekseevjk.ticketing.feature.airline.impl.presentation.main.entity

import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.Offer

fun Offer.toPopularDestinationWithImage(): OfferWithImage {
    return OfferWithImage(
        offer = this,
        imageResource = when (this.title) {
            "Die Antwoord" -> ru.alekseevjk.ticketing.design.R.drawable.die_antwoord
            "Socrat&Lera" -> ru.alekseevjk.ticketing.design.R.drawable.socrat_lera
            else -> ru.alekseevjk.ticketing.design.R.drawable.lampabikt
        }
    )
}