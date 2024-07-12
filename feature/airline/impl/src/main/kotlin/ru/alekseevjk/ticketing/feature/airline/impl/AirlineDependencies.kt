package ru.alekseevjk.ticketing.feature.airline.impl

import android.content.Context
import ru.alekseevjk.ticketing.core.di.ApplicationContext
import ru.alekseevjk.ticketing.core.di.Dependencies
import ru.alekseevjk.ticketing.feature.airline.impl.data.retrofit.GoogleDriveService
import ru.alekseevjk.ticketing.feature.airline.impl.data.room.dao.OfferDao
import ru.alekseevjk.ticketing.feature.airline.impl.data.room.dao.TicketDao
import ru.alekseevjk.ticketing.feature.airline.impl.data.room.dao.TicketOfferDao

interface AirlineDependencies : Dependencies {
    @ApplicationContext
    fun getContext(): Context

    val ticketDao: TicketDao
    val ticketOfferDao: TicketOfferDao
    val offerDao: OfferDao
    val googleDriveService: GoogleDriveService
}