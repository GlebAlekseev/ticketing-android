package ru.alekseevjk.ticketing.feature.airline.impl.domain.entity

import org.threeten.bp.LocalDateTime

data class Departure(
    val town: String,
    val date: LocalDateTime,
    val airport: String
)