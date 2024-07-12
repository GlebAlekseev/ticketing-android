package ru.alekseevjk.ticketing.feature.airline.impl.data.retrofit.dto

import com.google.gson.annotations.SerializedName

data class TicketsOffersResponse(
    @SerializedName("tickets_offers") val ticketsOffers: List<TicketOfferDto>
)