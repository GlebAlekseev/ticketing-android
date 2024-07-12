package ru.alekseevjk.ticketing.feature.airline.impl.presentation.search.mapper

import android.content.Context
import androidx.core.content.ContextCompat
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.TicketOffer
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.searchBottomSheet.entity.TicketOfferWithColor

fun List<TicketOffer>.mapToPopularDestinationWithImage(context: Context): List<TicketOfferWithColor> {
    return this.mapIndexed { index, ticketOffer ->
        TicketOfferWithColor(
            ticketOffer = ticketOffer,
            color = when (index % 3) {
                0 -> ru.alekseevjk.ticketing.design.R.color.red.toColor(context)
                1 -> ru.alekseevjk.ticketing.design.R.color.blue.toColor(context)
                else -> ru.alekseevjk.ticketing.design.R.color.white.toColor(context)
            }
        )
    }
}

private fun Int.toColor(context: Context): Int = ContextCompat.getColor(context, this)