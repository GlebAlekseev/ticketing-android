package ru.alekseevjk.ticketing.feature.airline.impl.presentation.searchBottomSheet.mapper

import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.PopularDestination
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.searchBottomSheet.entity.PopularDestinationWithImage

fun PopularDestination.toPopularDestinationWithImage(): PopularDestinationWithImage {
    return PopularDestinationWithImage(
        popularDestination = this,
        imageResource = when (this.destinationName) {
            "Сочи" -> ru.alekseevjk.ticketing.design.R.drawable.sochi
            "Стамбул" -> ru.alekseevjk.ticketing.design.R.drawable.istanbul
            else -> ru.alekseevjk.ticketing.design.R.drawable.phuket
        }
    )
}