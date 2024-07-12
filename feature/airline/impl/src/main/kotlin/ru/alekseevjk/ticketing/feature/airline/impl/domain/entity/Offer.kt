package ru.alekseevjk.ticketing.feature.airline.impl.domain.entity

data class Offer(
    val id: Int,
    val title: String,
    val town: String,
    val price: Price
)