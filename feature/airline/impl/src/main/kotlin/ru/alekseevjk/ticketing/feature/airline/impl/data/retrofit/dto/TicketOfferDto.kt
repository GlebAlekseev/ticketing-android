package ru.alekseevjk.ticketing.feature.airline.impl.data.retrofit.dto

import com.google.gson.annotations.SerializedName

data class TicketOfferDto(
    val id: Int,
    val title: String,
    @SerializedName("time_range") val timeRange: List<String>,
    val price: PriceDto
)