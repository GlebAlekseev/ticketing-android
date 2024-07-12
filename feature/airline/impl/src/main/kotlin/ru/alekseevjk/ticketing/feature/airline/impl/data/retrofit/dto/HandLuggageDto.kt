package ru.alekseevjk.ticketing.feature.airline.impl.data.retrofit.dto

import com.google.gson.annotations.SerializedName

data class HandLuggageDto(
    @SerializedName("has_hand_luggage") val hasHandLuggage: Boolean,
    val size: String?
)