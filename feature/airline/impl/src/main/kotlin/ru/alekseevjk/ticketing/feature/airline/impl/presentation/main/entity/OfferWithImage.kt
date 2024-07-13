package ru.alekseevjk.ticketing.feature.airline.impl.presentation.main.entity

import androidx.annotation.DrawableRes
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.Offer

data class OfferWithImage(
    @DrawableRes val imageResource: Int,
    val offer: Offer,
)