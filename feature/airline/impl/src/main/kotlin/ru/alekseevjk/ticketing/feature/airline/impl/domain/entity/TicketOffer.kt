package ru.alekseevjk.ticketing.feature.airline.impl.domain.entity

data class TicketOffer(
    val id: Int,
    val title: String,
    val timeRange: List<String>,
    val price: Price,
)