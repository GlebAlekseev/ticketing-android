package ru.alekseevjk.ticketing.feature.airline.impl.data.retrofit.dto

data class OfferDto(
    val id: Int,
    val title: String,
    val town: String,
    val price: PriceDto
)