package ru.alekseevjk.ticketing.feature.airline.impl.domain.repository

import kotlinx.coroutines.flow.Flow

interface AirlineConfigRepository {
    var lastDepartureTown: String?
    var withoutTransfers: Boolean
    var withoutTransfersAsFlow: Flow<Boolean>
    var onlyWithLuggage: Boolean
    var onlyWithLuggageAsFlow: Flow<Boolean>
}