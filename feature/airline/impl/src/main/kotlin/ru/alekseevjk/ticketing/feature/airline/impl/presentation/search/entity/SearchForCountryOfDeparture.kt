package ru.alekseevjk.ticketing.feature.airline.impl.presentation.search.entity

import android.os.Parcelable
import org.threeten.bp.LocalDate

@kotlinx.parcelize.Parcelize
data class SearchForCountryOfDeparture(
    val departureTown: String,
    val arrivalTown: String,
    val classType: String = "1, эконом",
    val dateDeparture: LocalDate = LocalDate.now(),
    val dateReturnBack: LocalDate? = null,
    val withoutTransfers: Boolean? = null,
    val onlyWithLuggage: Boolean? = null
) : Parcelable