package ru.alekseevjk.ticketing.feature.airline.impl.presentation.searchBottomSheet.entity

import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.TicketOffer

data class TicketOfferWithColor(
    val ticketOffer: TicketOffer,
    val color: Int
)