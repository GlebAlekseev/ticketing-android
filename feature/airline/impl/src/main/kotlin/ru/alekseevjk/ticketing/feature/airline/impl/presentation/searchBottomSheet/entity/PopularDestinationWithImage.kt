package ru.alekseevjk.ticketing.feature.airline.impl.presentation.searchBottomSheet.entity

import androidx.annotation.DrawableRes
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.PopularDestination

data class PopularDestinationWithImage(
    @DrawableRes val imageResource: Int,
    val popularDestination: PopularDestination
)
