package ru.alekseevjk.ticketing.feature.airline.impl.data.retrofit.dto

import com.google.gson.annotations.SerializedName

data class TicketDto(
    val id: Int,
    val badge: String?,
    val price: PriceDto,
    @SerializedName("provider_name") val providerName: String,
    val company: String,
    val departure: DepartureDto,
    val arrival: ArrivalDto,
    @SerializedName("has_transfer") val hasTransfer: Boolean,
    @SerializedName("has_visa_transfer") val hasVisaTransfer: Boolean,
    val luggage: LuggageDto,
    @SerializedName("hand_luggage") val handLuggage: HandLuggageDto,
    @SerializedName("is_returnable") val isReturnable: Boolean,
    @SerializedName("is_exchangable") val isExchangable: Boolean
)