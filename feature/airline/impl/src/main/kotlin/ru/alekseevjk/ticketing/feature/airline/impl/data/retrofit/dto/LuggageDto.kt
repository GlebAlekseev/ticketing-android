package ru.alekseevjk.ticketing.feature.airline.impl.data.retrofit.dto

import com.google.gson.annotations.SerializedName

data class LuggageDto(
    @SerializedName("has_luggage") val hasLuggage: Boolean,
    val price: PriceDto?
)